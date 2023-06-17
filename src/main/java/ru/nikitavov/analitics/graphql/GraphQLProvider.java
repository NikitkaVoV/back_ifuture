package ru.nikitavov.analitics.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Configuration
public class GraphQLProvider implements WebMvcConfigurer {

    private final GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        TypeDefinitionRegistry typeRegistry;

        try (var schemaStream = new ClassPathResource("schema.graphqls").getInputStream()) {
            typeRegistry = schemaParser.parse(new InputStreamReader(schemaStream, StandardCharsets.UTF_8));
        }

        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema schema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("layers", graphQLDataFetchers.getLayerDataFetcher()))
                .build();
    }

    @PostConstruct
    public void init() {
        // Можно выполнить некоторую инициализацию здесь
    }
}
