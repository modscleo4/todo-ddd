package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record PatchNoteDTO(
    @Valid @Size(max = 255) String title,
    @Valid String content,
    @Valid Boolean done
) {

}
