package br.dev.modscleo4.todo.application.note;

import br.dev.modscleo4.todo.domain.note.Note;
import br.dev.modscleo4.todo.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NoteRepository extends CrudRepository<Note, UUID> {
    Page<Note> findAllByUser(User user, Pageable pageable);
}
