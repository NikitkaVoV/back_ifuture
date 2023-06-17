package ru.nikitavov.analitics.database.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link ru.nikitavov.analitics.database.model.Figure}
 */
public record FigureDto(Integer id, Integer value, TypeDto type, List<PointDto> points) implements Serializable {
}