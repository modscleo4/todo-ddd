package br.dev.modscleo4.todo.domain.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ProfileAlreadyExistsException extends HttpClientErrorException {
    public ProfileAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Profile already exists for this user");
    }
}
