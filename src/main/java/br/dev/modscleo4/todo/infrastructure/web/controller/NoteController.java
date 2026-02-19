package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.application.note.NoteService;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.web.dto.CreateNoteDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.NoteInfoDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.PatchNoteDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<NoteInfoDTO> getAll(
        @Parameter(hidden = true) Authentication authentication,
        Pageable pageable
    ) {
        return service.getAll((User) authentication.getPrincipal(), pageable).map(NoteInfoDTO::new);
    }

    /**
     * Cria uma nova nota para o usuário.
     */
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoteInfoDTO save(@RequestBody CreateNoteDTO data, @Parameter(hidden = true) Authentication authentication) {
        return new NoteInfoDTO(service.create((User) authentication.getPrincipal(), data.title(), data.content()));
    }

    /**
     * Retorna uma nota específica do usuário.
     */
    @GetMapping("/{id}")
    public NoteInfoDTO get(@PathVariable String id, @Parameter(hidden = true) Authentication authentication) {
        return new NoteInfoDTO(service.get((User) authentication.getPrincipal(), id));
    }

    /**
     * Atualiza uma nota específica do usuário.
     */
    @PatchMapping("/{id}")
    public NoteInfoDTO update(
        @PathVariable String id,
        @RequestBody PatchNoteDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        return new NoteInfoDTO(service.update((User) authentication.getPrincipal(), id, data.title(), data.content(), data.done()));
    }

    /**
     * Exclui uma nota específica do usuário.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, @Parameter(hidden = true) Authentication authentication) {
        service.delete((User) authentication.getPrincipal(), id);
    }
}
