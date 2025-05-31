package br.mackenzie.bahtche.service;

import br.mackenzie.bahtche.dto.ComentarioDTO;
import br.mackenzie.bahtche.dto.ComentarioRequest;
import br.mackenzie.bahtche.model.Comentario;
import br.mackenzie.bahtche.model.Problema;
import br.mackenzie.bahtche.model.Usuario;
import br.mackenzie.bahtche.repository.ComentarioRepository;
import br.mackenzie.bahtche.repository.ProblemaRepository;
import br.mackenzie.bahtche.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ProblemaRepository problemaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LogAlteracaoService logAlteracaoService;

    public ComentarioService(ComentarioRepository comentarioRepository, ProblemaRepository problemaRepository, UsuarioRepository usuarioRepository, LogAlteracaoService logAlteracaoService) {
        this.comentarioRepository = comentarioRepository;
        this.problemaRepository = problemaRepository;
        this.usuarioRepository = usuarioRepository;
        this.logAlteracaoService = logAlteracaoService;
    }

    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }

    public Comentario buscarPorId(Long id) {
        return comentarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }

    public List<Comentario> listarPorProblema(Long problemaId) {
        return comentarioRepository.findByProblemaId(problemaId);
    }

    public Comentario salvarViaDTO(ComentarioRequest dto) {
        Problema problema = problemaRepository.findById(dto.getProblemaId()).orElseThrow(() -> new RuntimeException("Problema não encontrado"));
        Usuario admin = usuarioRepository.findById(dto.getAdministradorId()).orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setProblema(problema);
        comentario.setAdministrador(admin);

        Comentario salvo = comentarioRepository.save(comentario);

        logAlteracaoService.registrarComentario(comentario.getTexto(), admin, problema);

        return salvo;
    }

    public void deletar(Long id) {
        comentarioRepository.deleteById(id);
    }

    public ComentarioDTO converterParaDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setTexto(comentario.getTexto());
        dto.setDataCriacao(comentario.getDataCriacao());
    
        if (comentario.getAdministrador() != null) {
            dto.setNomeAdministrador(comentario.getAdministrador().getNome());
        }
    
        return dto;
    }
}
