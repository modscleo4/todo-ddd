package br.dev.modscleo4.todo.domain.auth;

public final class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
