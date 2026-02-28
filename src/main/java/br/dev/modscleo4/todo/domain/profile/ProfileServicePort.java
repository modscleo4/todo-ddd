package br.dev.modscleo4.todo.domain.profile;

import br.dev.modscleo4.todo.domain.user.User;

import java.util.Date;

public interface ProfileServicePort {
    Profile create(User user, String name, String cpf, Date birthDate);

    Profile update(Profile profile, String name);
}
