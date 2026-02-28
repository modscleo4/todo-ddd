package br.dev.modscleo4.todo.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

}
