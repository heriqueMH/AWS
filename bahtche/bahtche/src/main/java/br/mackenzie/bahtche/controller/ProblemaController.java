package br.mackenzie.bahtche.controller;

import br.mackenzie.bahtche.dto.ProblemaDTO;
import br.mackenzie.bahtche.model.Problema;
import br.mackenzie.bahtche.service.ProblemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/problemas")
public class ProblemaController {

    private final ProblemaService problemaService;

    public ProblemaController(ProblemaService problemaService) {
        this.problemaService = problemaService;
    }

    @GetMapping
    public ResponseEntity<List<ProblemaDTO>> listarProblemas() {
        List<Problema> problemas = problemaService.listarProblemas();
        List<ProblemaDTO> dtos = problemas.stream()
            .map(problemaService::converterParaDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemaDTO> buscarPorId(@PathVariable Long id) {
        Problema problema = problemaService.buscarPorId(id);
        ProblemaDTO dto = problemaService.converterParaDTO(problema);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Problema> salvar(@RequestBody Problema problema) {
        Problema salvo = problemaService.salvar(problema);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}/moderador/{moderadorId}")
    public ResponseEntity<Problema> atualizar(@PathVariable Long id,
                                              @RequestBody Problema problema,
                                              @PathVariable Long moderadorId) {
        Problema atualizado = problemaService.atualizarPorModerador(id, problema, moderadorId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}/moderador/{moderadorId}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @PathVariable Long moderadorId) {
        problemaService.deletarPorModerador(id, moderadorId);
        return ResponseEntity.noContent().build();
    }
}
