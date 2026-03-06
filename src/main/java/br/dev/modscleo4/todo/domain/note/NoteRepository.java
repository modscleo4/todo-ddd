package br.dev.modscleo4.todo.domain.note;

import br.dev.modscleo4.todo.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface NoteRepository {
    Optional<Note> findById(UUID id);

    Page<Note> findAllByUser(User user, Pageable pageable);

    Note save(Note note);

    void delete(Note note);
}
