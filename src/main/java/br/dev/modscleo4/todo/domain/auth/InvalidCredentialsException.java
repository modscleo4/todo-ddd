package br.dev.modscleo4.todo.domain.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InvalidCredentialsException extends HttpClientErrorException {
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
