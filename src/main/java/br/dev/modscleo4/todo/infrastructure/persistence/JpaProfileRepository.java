package br.dev.modscleo4.todo.infrastructure.persistence;

import br.dev.modscleo4.todo.domain.profile.Profile;
import br.dev.modscleo4.todo.domain.profile.ProfileRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Primary
@Repository
public interface JpaProfileRepository extends JpaRepository<Profile, UUID>, ProfileRepository {

}
