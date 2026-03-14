package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

public record PatchProfileDTO(
    @Valid @Max(255) String name
) {

}
