package br.dev.modscleo4.todo.domain.profile;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    
}
