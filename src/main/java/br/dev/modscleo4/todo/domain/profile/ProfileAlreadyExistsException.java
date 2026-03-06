package br.dev.modscleo4.todo.domain.profile;

public class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException() {
        super("Profile already exists for this user");
    }
}
