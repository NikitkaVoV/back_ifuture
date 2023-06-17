package ru.nikitavov.analitics.database.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.nikitavov.analitics.database.model.Layer}
 */
public record LayerDto(Integer id, @NotBlank String name, List<FigureDto> figures) implements Serializable {
}