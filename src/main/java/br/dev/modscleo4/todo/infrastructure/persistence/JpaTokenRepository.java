package br.dev.modscleo4.todo.infrastructure.persistence;

import br.dev.modscleo4.todo.domain.auth.Token;
import br.dev.modscleo4.todo.domain.auth.TokenRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Primary
@Repository
public interface JpaTokenRepository extends JpaRepository<Token, UUID>, TokenRepository {

}
