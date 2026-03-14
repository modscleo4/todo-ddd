package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateNoteDTO(
    @Valid @NotEmpty @Size(max = 255) String title,
    @Valid @NotEmpty String content
) {

}
