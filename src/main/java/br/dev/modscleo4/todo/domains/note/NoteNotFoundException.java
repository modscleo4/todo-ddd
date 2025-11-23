package br.dev.modscleo4.todo.domains.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class NoteNotFoundException extends HttpClientErrorException {
    public NoteNotFoundException() {
        super(HttpStatus.FORBIDDEN, "Note not found");
    }
}
