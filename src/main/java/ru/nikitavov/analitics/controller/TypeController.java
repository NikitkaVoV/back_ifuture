package ru.nikitavov.analitics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("types")
public class TypeController {

    @GetMapping
    public String[] login(@RequestBody String login, @RequestBody String password) {
        return new String[0];
    }

    @GetMapping("{id}")
    public String dg(@PathVariable String id) {
        return "";
    }

    @DeleteMapping("{id}")
    public String login(@PathVariable String id) {
        return "";
    }
}
