package br.mackenzie.bahtche.service;

import br.mackenzie.bahtche.dto.LogAlteracaoDTO;
import br.mackenzie.bahtche.model.LogAlteracao;
import br.mackenzie.bahtche.model.Problema;
import br.mackenzie.bahtche.model.Usuario;
import br.mackenzie.bahtche.repository.LogAlteracaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogAlteracaoService {

    private final LogAlteracaoRepository logRepository;

    public LogAlteracaoService(LogAlteracaoRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LogAlteracao salvar(LogAlteracao log) {
        return logRepository.save(log);
    }

    public void registrarCriacao(Problema problema) {
        String descricao = "Novo reporte criado: " + problema.getTitulo();
        logRepository.save(new LogAlteracao(descricao, problema.getUsuario(), problema));
    }

    public void registrarAlteracao(String descricao, Usuario admin, Problema problema) {
        logRepository.save(new LogAlteracao(descricao, admin, problema));
    }

    public void registrarComentario(String textoComentario, Usuario admin, Problema problema) {
        String descricao = "Comentário adicionado: \"" + textoComentario + "\"";
        logRepository.save(new LogAlteracao(descricao, admin, problema));
    }

    public List<LogAlteracao> listarTodos() {
        return logRepository.findAll();
    }

    public LogAlteracaoDTO converterParaDTO(LogAlteracao log) {
        LogAlteracaoDTO dto = new LogAlteracaoDTO();
        dto.setId(log.getId());
        dto.setDescricao(log.getDescricao());
        dto.setDataAlteracao(log.getDataAlteracao());
    
        if (log.getAdministrador() != null) {
            dto.setNomeAdministrador(log.getAdministrador().getNome());
        }
    
        if (log.getProblema() != null) {
            dto.setDataCriacaoProblema(log.getProblema().getDataCriacao());
        }
    
        return dto;
    }

}