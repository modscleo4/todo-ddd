package br.dev.modscleo4.todo.infrastructure.persistence;

import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.domain.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID>, UserRepository {
    @Query("""
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.profile p
        WHERE u.email = ?1""")
    Optional<User> findByEmail(String email);
}
