package br.dev.modscleo4.todo.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository {
    Token getReferenceById(UUID id);

    Token save(Token token);
}
