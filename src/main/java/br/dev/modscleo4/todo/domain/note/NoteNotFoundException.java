package br.dev.modscleo4.todo.domain.note;

public final class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
        super("Note not found");
    }
}
