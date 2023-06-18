package ru.nikitavov.analitics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController("gpt")
public class ChatController {

    private static final String apiKey = "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";
    private static final String apiUrl = "https://api.openai.com/v1/completions";
    private final RestTemplate restTemplate;

    @PostMapping("message")
    @ResponseBody
    public String chat(@RequestBody String userMessage) throws JsonProcessingException {
        // Создание заголовков запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Создание промпта с именем "John"
        String prompt = "USER: " + userMessage + "\nAI: System:";
        String jsonRequest = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";
        // Преобразование JSON-тела запроса с помощью ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(jsonRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Отправка POST-запроса и получение ответа
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Обработка ответа и извлечение сообщения от ChatGPT
        String jsonResponse = responseEntity.getBody();
        String chatGptResponse = jsonResponse.substring(jsonResponse.indexOf("text\":\"") + 7, jsonResponse.lastIndexOf("\""));

        return chatGptResponse;
    }

}
