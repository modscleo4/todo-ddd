package br.dev.modscleo4.todo.domains.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ProfileAlreadyExistsException extends HttpClientErrorException {
    public ProfileAlreadyExistsException() {
        super(HttpStatus.UNPROCESSABLE_CONTENT, "Profile already exists for this user");
    }
}
