package br.mackenzie.bahtche.controller;

import br.mackenzie.bahtche.dto.LogAlteracaoDTO;
import br.mackenzie.bahtche.model.LogAlteracao;
import br.mackenzie.bahtche.repository.LogAlteracaoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/logs")
public class LogAlteracaoController {

    private final LogAlteracaoRepository logAlteracaoRepository;

    public LogAlteracaoController(LogAlteracaoRepository logAlteracaoRepository) {
        this.logAlteracaoRepository = logAlteracaoRepository;
    }

    @GetMapping
    public List<LogAlteracaoDTO> listarTodos() {
        List<LogAlteracao> logs = logAlteracaoRepository.findAll(Sort.by(Sort.Direction.DESC, "dataAlteracao"));
        return logs.stream()
                   .map(LogAlteracaoDTO::new)
                   .collect(Collectors.toList());
    }
}
