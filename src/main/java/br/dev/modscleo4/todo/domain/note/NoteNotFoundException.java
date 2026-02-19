package br.dev.modscleo4.todo.domain.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class NoteNotFoundException extends HttpClientErrorException {
    public NoteNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Note not found");
    }
}
