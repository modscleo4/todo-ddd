package br.dev.modscleo4.todo.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UserNotFoundException extends HttpClientErrorException {
    public UserNotFoundException() {
        super(HttpStatus.UNAUTHORIZED, "User not found");
    }
}
