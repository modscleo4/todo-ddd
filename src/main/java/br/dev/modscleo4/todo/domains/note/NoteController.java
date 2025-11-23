package br.dev.modscleo4.todo.domains.note;

import br.dev.modscleo4.todo.domains.note.dto.CreateNoteDTO;
import br.dev.modscleo4.todo.domains.note.dto.PatchNoteDTO;
import br.dev.modscleo4.todo.domains.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gerencia as notas do usuário.
 */
@RestController
@RequestMapping("/v1/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {
    private final NoteService service;

    /**
     * Retorna todas as notas do usuário.
     */
    @GetMapping("/")
    public Iterable<Note> getAll(@Parameter(hidden = true) Authentication authentication) {
        return service.getAll((User) authentication.getPrincipal());
    }

    /**
     * Cria uma nova nota para o usuário.
     */
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Note save(@RequestBody CreateNoteDTO data, @Parameter(hidden = true) Authentication authentication) {
        return service.create((User) authentication.getPrincipal(), data.title(), data.content());
    }

    /**
     * Retorna uma nota específica do usuário.
     */
    @GetMapping("/{id}")
    public Note get(@PathVariable("id") String id, @Parameter(hidden = true) Authentication authentication) {
        return service.get((User) authentication.getPrincipal(), id);
    }

    /**
     * Atualiza uma nota específica do usuário.
     */
    @PatchMapping("/{id}")
    public Note update(
        @PathVariable("id") String id,
        @RequestBody PatchNoteDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        return service.update((User) authentication.getPrincipal(), id, data.title(), data.content(), data.done());
    }

    /**
     * Exclui uma nota específica do usuário.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id, @Parameter(hidden = true) Authentication authentication) {
        service.delete((User) authentication.getPrincipal(), id);
    }
}
