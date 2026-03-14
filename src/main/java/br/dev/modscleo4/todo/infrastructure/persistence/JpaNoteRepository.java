package br.dev.modscleo4.todo.infrastructure.persistence;

import br.dev.modscleo4.todo.domain.note.Note;
import br.dev.modscleo4.todo.domain.note.NoteRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Primary
@Repository
public interface JpaNoteRepository extends JpaRepository<Note, UUID>, NoteRepository {

}
