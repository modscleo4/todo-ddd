package br.dev.modscleo4.todo.domains.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ProfileNotFoundException extends HttpClientErrorException {
    public ProfileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Profile not found");
    }
}
