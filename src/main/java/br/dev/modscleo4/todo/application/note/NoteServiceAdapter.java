package br.dev.modscleo4.todo.application.note;

import br.dev.modscleo4.todo.domain.note.Note;
import br.dev.modscleo4.todo.domain.note.NoteNotFoundException;
import br.dev.modscleo4.todo.domain.note.NoteRepository;
import br.dev.modscleo4.todo.domain.note.NoteServicePort;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceAdapter implements NoteServicePort {
    private final NoteRepository repository;

    @Transactional(readOnly = true)
    public Page<Note> getAll(User owner, Pageable pageable) {
        return repository.findAllByUser(owner, pageable);
    }

    @Transactional
    public Note create(User owner, String title, String content) {
        log.info("Creating note...");

        var note = new Note();
        note.setUser(owner);
        note.setTitle(title);
        note.setContent(content);

        return repository.save(note);
    }

    @Transactional(readOnly = true)
    public Note get(User owner, String id) {
        try (var _ = MDC.putCloseable("noteId", id)) {
            var note = repository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
            if (!note.getUser().getId().equals(owner.getId()) && !owner.getAuthorities().contains(UserRole.ADMIN)) {
                throw new NoteNotFoundException();
            }

            return note;
        }
    }

    @Transactional
    public Note update(User owner, String id, String title, String content, Boolean done) {
        try (var _ = MDC.putCloseable("noteId", id)) {
            var note = repository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
            if (!note.getUser().getId().equals(owner.getId()) && !owner.getAuthorities().contains(UserRole.ADMIN)) {
                throw new NoteNotFoundException();
            }

            if (title != null) note.setTitle(title);
            if (content != null) note.setContent(content);
            if (done != null) note.setDone(done);

            return repository.save(note);
        }
    }

    @Transactional
    public void delete(User owner, String id) {
        try (var _ = MDC.putCloseable("noteId", id)) {
            var note = repository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
            if (!note.getUser().getId().equals(owner.getId()) && !owner.getAuthorities().contains(UserRole.ADMIN)) {
                throw new NoteNotFoundException();
            }

            repository.delete(note);
        }
    }
}
