package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.domain.note.NoteServicePort;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.web.dto.CreateNoteDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.NoteInfoDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.PatchNoteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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

@RestController
@RequestMapping("/v1/notes")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Notas", description = "Gerenciamento das notas do usuário.")
@RequiredArgsConstructor
@Slf4j
public class NoteController {
    private final NoteServicePort service;

    @GetMapping("/")
    @Operation(summary = "Retorna todas as notas do usuário.")
    public Page<NoteInfoDTO> getAll(
        @Parameter(hidden = true) Authentication authentication,
        @ParameterObject Pageable pageable
    ) {
        return service.getAll((User) authentication.getPrincipal(), pageable).map(NoteInfoDTO::new);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova nota para o usuário.")
    public NoteInfoDTO save(@RequestBody CreateNoteDTO data, @Parameter(hidden = true) Authentication authentication) {
        return new NoteInfoDTO(service.create((User) authentication.getPrincipal(), data.title(), data.content()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma nota específica do usuário.")
    public NoteInfoDTO get(@PathVariable String id, @Parameter(hidden = true) Authentication authentication) {
        return new NoteInfoDTO(service.get((User) authentication.getPrincipal(), id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza uma nota específica do usuário.")
    public NoteInfoDTO update(
        @PathVariable String id,
        @RequestBody PatchNoteDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        return new NoteInfoDTO(service.update((User) authentication.getPrincipal(), id, data.title(), data.content(), data.done()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma nota específica do usuário.")
    public void delete(@PathVariable String id, @Parameter(hidden = true) Authentication authentication) {
        service.delete((User) authentication.getPrincipal(), id);
    }
}
