package ru.nikitavov.analitics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitavov.analitics.database.model.Figure;
import ru.nikitavov.analitics.database.model.Group;
import ru.nikitavov.analitics.database.model.Layer;
import ru.nikitavov.analitics.database.model.Point;
import ru.nikitavov.analitics.database.model.dto.*;
import ru.nikitavov.analitics.database.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("layer")
public class LayerController {

    private final GroupRepository groupRepository;

    @GetMapping
    public ResponseEntity<List<GroupDto>> allLayers() {
        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : groupRepository.findAll()) {
            List<LayerDto> layerDtos = new ArrayList<>();
            for (Layer layer : group.getLayers()) {
                List<FigureDto> figureDtos = new ArrayList<>();
                for (Figure figure : layer.getFigures()) {
                    List<PointDto> pointDtos = new ArrayList<>();
                    for (Point point : figure.getPoints()) {
                        PointDto pointDto = new PointDto(point.getId(), point.getLon(), point.getLat());
                        pointDtos.add(pointDto);
                    }
                    FigureDto figureDto = new FigureDto(figure.getId(), figure.getValue(), new TypeDto(figure.getType().getId(), figure.getType().getName()), pointDtos);
                    figureDtos.add(figureDto);
                }
                LayerDto layerDto = new LayerDto(layer.getId(), layer.getName(), figureDtos);
                layerDtos.add(layerDto);
            }
            GroupDto groupDto = new GroupDto(group.getId(), group.getName(), layerDtos);
            groupDtos.add(groupDto);
        }

        return ResponseEntity.ok(groupDtos);
    }
}
