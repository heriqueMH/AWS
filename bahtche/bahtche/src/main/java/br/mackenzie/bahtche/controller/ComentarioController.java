package br.mackenzie.bahtche.controller;

import br.mackenzie.bahtche.dto.ComentarioDTO;
import br.mackenzie.bahtche.dto.ComentarioRequest;
import br.mackenzie.bahtche.model.Comentario;
import br.mackenzie.bahtche.service.ComentarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public List<Comentario> listar() {
        return comentarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Comentario buscarPorId(@PathVariable Long id) {
        return comentarioService.buscarPorId(id);
    }

    @GetMapping("/problema/{problemaId}")
    public List<ComentarioDTO> listarPorProblema(@PathVariable Long problemaId) {
        return comentarioService.listarPorProblema(problemaId).stream()
            .map(comentarioService::converterParaDTO)
            .toList();
    }

    @PostMapping
    public Comentario criar(@RequestBody ComentarioRequest dto) {
        return comentarioService.salvarViaDTO(dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        comentarioService.deletar(id);
    }
}
