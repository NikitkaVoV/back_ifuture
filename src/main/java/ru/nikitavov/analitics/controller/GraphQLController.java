package ru.nikitavov.analitics.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GraphQLController {

    private final GraphQL graphQL;


    @PostMapping("/graphql")
    public ResponseEntity<Object> executeQuery(@RequestBody String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);

        return ResponseEntity.ok(executionResult.toSpecification());
    }
}
