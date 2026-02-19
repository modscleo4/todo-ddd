package br.dev.modscleo4.todo.application.note;

import br.dev.modscleo4.todo.TestUtils;
import br.dev.modscleo4.todo.domain.note.Note;
import br.dev.modscleo4.todo.domain.note.NoteNotFoundException;
import br.dev.modscleo4.todo.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @Test
    void create_shouldSetOwnerAndSave() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        // capture the saved note
        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        TestUtils.mockSave(noteRepository);

        var created = noteService.create(owner, "Title", "Content");

        verify(noteRepository, times(1)).save(captor.capture());
        var saved = captor.getValue();

        assertEquals("Title", saved.getTitle());
        assertEquals("Content", saved.getContent());
        assertSame(owner, saved.getUser());

        // returned value should be the saved note (mock returns the argument)
        assertSame(saved, created);
    }

    @Test
    void get_shouldThrowWhenOwnerMismatch() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var other = new User();
        other.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(other);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        assertThrows(NoteNotFoundException.class, () -> noteService.get(owner, note.getId().toString()));
    }

    @Test
    void get_shouldReturnNoteWhenOwnerMatches() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(owner);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        var found = noteService.get(owner, note.getId().toString());

        assertSame(note, found);
    }

    @Test
    void update_shouldThrowWhenOwnerMismatch() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var other = new User();
        other.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(other);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        assertThrows(NoteNotFoundException.class, () -> noteService.update(owner, note.getId().toString(), "T", "C", true));
    }

    @Test
    void update_shouldModifyAndSaveWhenOwnerMatches() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(owner);
        note.setTitle("Old");
        note.setContent("OldC");
        note.setDone(false);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        TestUtils.mockSave(noteRepository);

        var updated = noteService.update(owner, note.getId().toString(), "New", null, true);

        verify(noteRepository, times(1)).save(note);
        assertEquals("New", updated.getTitle());
        assertEquals("OldC", updated.getContent());
        assertTrue(updated.getDone());
    }

    @Test
    void delete_shouldThrowWhenOwnerMismatch() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var other = new User();
        other.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(other);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        assertThrows(NoteNotFoundException.class, () -> noteService.delete(owner, note.getId().toString()));
    }

    @Test
    void delete_shouldCallRepositoryWhenOwnerMatches() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        var note = new Note();
        note.setId(UUID.randomUUID());
        note.setUser(owner);

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        noteService.delete(owner, note.getId().toString());

        verify(noteRepository, times(1)).delete(note);
    }

    @Test
    void getAll_shouldDelegateToRepository() {
        var owner = new User();
        owner.setId(UUID.randomUUID());

        noteService.getAll(owner, Pageable.unpaged());

        verify(noteRepository, times(1)).findAllByUser(owner, Pageable.unpaged());
    }
}

