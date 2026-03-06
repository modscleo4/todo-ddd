package br.dev.modscleo4.todo.domain.profile;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("Profile not found");
    }
}
