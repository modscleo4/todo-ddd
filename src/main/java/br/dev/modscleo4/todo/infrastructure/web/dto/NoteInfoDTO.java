package br.dev.modscleo4.todo.infrastructure.web.dto;

import br.dev.modscleo4.todo.domain.note.Note;

import java.util.UUID;

public record NoteInfoDTO(
    UUID id,
    String title,
    String content,
    Boolean done
) {
    public NoteInfoDTO(Note note) {
        this(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getDone()
        );
    }
}
