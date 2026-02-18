package br.dev.modscleo4.todo.application.token;

import br.dev.modscleo4.todo.domain.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

}
