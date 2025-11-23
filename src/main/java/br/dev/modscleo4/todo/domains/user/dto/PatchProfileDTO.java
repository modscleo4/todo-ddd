package br.dev.modscleo4.todo.domains.user.dto;

import jakarta.validation.constraints.Max;

public record PatchProfileDTO(
    @Max(255) String name
) {

}
