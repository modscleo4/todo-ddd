package br.dev.modscleo4.todo.domains.note;

import br.dev.modscleo4.todo.domains.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NoteRepository extends CrudRepository<Note, UUID> {
    Iterable<Note> findAllByUser(User user);
}
