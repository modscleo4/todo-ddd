package br.dev.modscleo4.todo.application.note;

import br.dev.modscleo4.todo.domain.note.Note;
import br.dev.modscleo4.todo.domain.note.NoteNotFoundException;
import br.dev.modscleo4.todo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;

    @Transactional
    public Iterable<Note> getAll(User owner) {
        return noteRepository.findAllByUser(owner);
    }

    @Transactional
    public Page<Note> getAll(User owner, Pageable pageable) {
        return noteRepository.findAllByUser(owner, pageable);
    }

    @Transactional
    public Note create(User owner, String title, String content) {
        log.info("Creating note...");

        var note = new Note();
        note.setUser(owner);
        note.setTitle(title);
        note.setContent(content);

        return noteRepository.save(note);
    }

    @Transactional
    public Note get(User owner, String id) {
        var note = noteRepository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
        if (!note.getUser().getId().equals(owner.getId())) {
            throw new NoteNotFoundException();
        }

        return note;
    }

    @Transactional
    public Note update(User owner, String id, String title, String content, Boolean done) {
        var note = noteRepository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
        if (!note.getUser().getId().equals(owner.getId())) {
            throw new NoteNotFoundException();
        }

        if (title != null) note.setTitle(title);
        if (content != null) note.setContent(content);
        if (done != null) note.setDone(done);

        return noteRepository.save(note);
    }

    @Transactional
    public void delete(User owner, String id) {
        var note = noteRepository.findById(java.util.UUID.fromString(id)).orElseThrow(NoteNotFoundException::new);
        if (!note.getUser().getId().equals(owner.getId())) {
            throw new NoteNotFoundException();
        }

        noteRepository.delete(note);
    }
}
