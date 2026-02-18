package br.dev.modscleo4.todo.infrastructure.web.dto;

import jakarta.validation.constraints.Max;

public record PatchProfileDTO(
    @Max(255) String name
) {

}
