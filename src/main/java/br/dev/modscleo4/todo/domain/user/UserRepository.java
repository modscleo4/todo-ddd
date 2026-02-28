package br.dev.modscleo4.todo.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    @Query("""
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.profile p
        WHERE u.email = ?1""")
    Optional<User> findByEmail(String email);
}
