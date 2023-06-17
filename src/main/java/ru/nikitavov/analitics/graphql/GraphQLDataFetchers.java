package ru.nikitavov.analitics.graphql;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nikitavov.analitics.controller.LayerController;
import ru.nikitavov.analitics.database.model.dto.GroupDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GraphQLDataFetchers {

    private final LayerController layerController;

    public DataFetcher<List<GroupDto>> getLayerDataFetcher() {
        return environment -> layerController.allLayers().getBody();
    }
}
