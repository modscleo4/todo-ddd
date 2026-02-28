package br.dev.modscleo4.todo.domain.note;

import br.dev.modscleo4.todo.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteServicePort {
    Page<Note> getAll(User owner, Pageable pageable);

    Note create(User owner, String title, String content);

    Note get(User owner, String id);

    Note update(User owner, String id, String title, String content, Boolean done);

    void delete(User owner, String id);
}
