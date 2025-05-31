package br.mackenzie.bahtche.service;

import br.mackenzie.bahtche.model.LogAlteracao;
import br.mackenzie.bahtche.model.Problema;
import br.mackenzie.bahtche.model.Usuario;
import br.mackenzie.bahtche.repository.LogAlteracaoRepository;
import br.mackenzie.bahtche.repository.ProblemaRepository;
import br.mackenzie.bahtche.repository.UsuarioRepository;
import br.mackenzie.bahtche.dto.ProblemaDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProblemaService {

    private final ProblemaRepository problemaRepository;
    private final LogAlteracaoService logAlteracaoService;
    private final UsuarioRepository usuarioRepository;
    private final LogAlteracaoRepository logAlteracaoRepository;

    public ProblemaService(ProblemaRepository problemaRepository,
                           LogAlteracaoService logAlteracaoService,
                           UsuarioRepository usuarioRepository,
                           LogAlteracaoRepository logAlteracaoRepository) {
        this.problemaRepository = problemaRepository;
        this.logAlteracaoService = logAlteracaoService;
        this.usuarioRepository = usuarioRepository;
        this.logAlteracaoRepository = logAlteracaoRepository;
    }

    public List<Problema> listarProblemas() {
        return problemaRepository.findAll();
    }

    public Problema buscarPorId(Long id) {
        return problemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problema não encontrado"));
    }

    public Problema salvar(Problema problema) {
        problema.setDataAtualizacao(new Date());
        Problema salvo = problemaRepository.save(problema);

        Usuario autor = problema.getUsuario();
        String descricao = "Novo reporte criado: " + salvo.getTitulo();
        logAlteracaoService.registrarAlteracao(descricao, autor, salvo);

        return salvo;
    }

    public Problema atualizarPorModerador(Long id, Problema problemaAtualizado, Long moderadorId) {
        Problema existente = buscarPorId(id);

        existente.setTitulo(problemaAtualizado.getTitulo());
        existente.setDescricao(problemaAtualizado.getDescricao());
        existente.setCategoria(problemaAtualizado.getCategoria());
        existente.setStatus(problemaAtualizado.getStatus());
        existente.setEndereco(problemaAtualizado.getEndereco());
        existente.setDataAtualizacao(new Date());

        Problema atualizado = problemaRepository.save(existente);

        Usuario moderador = usuarioRepository.findById(moderadorId)
            .orElseThrow(() -> new RuntimeException("Moderador não encontrado"));

        String descricao = String.format("Status do reporte '%s' atualizado para: %s",
                                          existente.getTitulo(), existente.getStatus());
        logAlteracaoService.registrarAlteracao(descricao, moderador, existente);

        return atualizado;
    }

    public void deletarPorModerador(Long problemaId, Long moderadorId) {
        Problema problema = buscarPorId(problemaId);
        Usuario moderador = usuarioRepository.findById(moderadorId)
            .orElseThrow(() -> new RuntimeException("Moderador não encontrado"));

        problemaRepository.deleteById(problemaId);

        String descricao = String.format("Reporte removido: %s", problema.getTitulo());
        logAlteracaoService.registrarAlteracao(descricao, moderador, problema);
    }

    public ProblemaDTO converterParaDTO(Problema problema) {
        ProblemaDTO dto = new ProblemaDTO();
        dto.setId(problema.getId());
        dto.setTitulo(problema.getTitulo());
        dto.setDescricao(problema.getDescricao());
        dto.setCategoria(problema.getCategoria());
        dto.setStatus(problema.getStatus());
        dto.setEndereco(problema.getEndereco()); 
        dto.setDataAtualizacao(problema.getDataAtualizacao());
        dto.setDataCriacao(problema.getDataCriacao());
    
        if (problema.getUsuario() != null) {
            dto.setNomeUsuarioCriador(problema.getUsuario().getNome());
            dto.setUsuarioId(problema.getUsuario().getId()); 
        }
    
        LogAlteracao ultima = logAlteracaoRepository.findTopByProblemaIdOrderByDataAlteracaoDesc(problema.getId());
        if (ultima != null) {
            dto.setDataUltimaAlteracao(ultima.getDataAlteracao());
            dto.setNomeAdministradorUltimaAlteracao(ultima.getAdministrador().getNome());
        }
    
        return dto;
    }
}
