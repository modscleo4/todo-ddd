package br.dev.modscleo4.todo.domains.note.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateNoteDTO(
    @NotEmpty @Size(max = 255) String title,
    @NotEmpty String content
) {

}
