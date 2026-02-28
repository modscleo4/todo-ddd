package br.dev.modscleo4.todo.domain.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserServicePort {
    Optional<UserDetails> authenticate(String email, String password);

    User create(String email, String password);
}
