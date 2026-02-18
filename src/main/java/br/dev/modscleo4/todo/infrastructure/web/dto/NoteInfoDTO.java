package br.dev.modscleo4.todo.infrastructure.web.dto;

import br.dev.modscleo4.todo.domain.note.Note;

public record NoteInfoDTO(
    String title,
    String content,
    Boolean done
) {
    public NoteInfoDTO(Note note) {
        this(
            note.getTitle(),
            note.getContent(),
            note.getDone()
        );
    }
}
