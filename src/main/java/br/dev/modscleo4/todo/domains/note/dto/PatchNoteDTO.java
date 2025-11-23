package br.dev.modscleo4.todo.domains.note.dto;

import jakarta.validation.constraints.Size;

public record PatchNoteDTO(
    @Size(max = 255) String title,
    String content,
    Boolean done
) {

}
