package br.dev.modscleo4.todo.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserServicePort {
    Optional<UserDetails> authenticate(String email, String password);

    Page<User> getAll(Pageable pageable);

    User create(String email, String password);
}
