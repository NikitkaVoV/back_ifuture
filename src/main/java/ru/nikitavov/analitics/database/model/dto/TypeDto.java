package ru.nikitavov.analitics.database.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link ru.nikitavov.analitics.database.model.Type}
 */
public record TypeDto(Integer id, @NotBlank String name) implements Serializable {
}