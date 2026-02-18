package br.dev.modscleo4.todo.application.profile;

import br.dev.modscleo4.todo.domain.profile.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    
}
