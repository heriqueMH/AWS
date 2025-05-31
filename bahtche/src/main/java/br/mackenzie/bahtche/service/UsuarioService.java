package br.mackenzie.bahtche.service;

import br.mackenzie.bahtche.dto.UsuarioDTO;
import br.mackenzie.bahtche.model.Usuario;
import br.mackenzie.bahtche.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final LogAlteracaoService logAlteracaoService;

    public UsuarioService(UsuarioRepository usuarioRepository, LogAlteracaoService logAlteracaoService) {
        this.usuarioRepository = usuarioRepository;
        this.logAlteracaoService = logAlteracaoService;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarUsuarioPorId(id);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setAtivo(usuarioAtualizado.getAtivo());
        usuario.setRole(usuarioAtualizado.getRole());

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticarUsuario(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este e-mail."));

        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta.");
        }

        return usuario;
    }

    public Usuario alterarStatus(Long id, boolean ativo, Long moderadorId) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setAtivo(ativo);
        Usuario moderador = buscarUsuarioPorId(moderadorId);
        usuarioRepository.save(usuario);

        String acao = ativo ? "reativado" : "desativado";
        String descricao = "Status alterado: Usuário " + usuario.getNome() + " foi " + acao;
        logAlteracaoService.registrarAlteracao(descricao, moderador, null);

        return usuario;
    }

    public Usuario promoverParaModerador(Long id, Long moderadorId) {
        Usuario usuario = buscarUsuarioPorId(id);

        if (!"MODERADOR".equals(usuario.getRole())) {
            usuario.setRole("MODERADOR");
            usuarioRepository.save(usuario);

            Usuario moderador = buscarUsuarioPorId(moderadorId);
            String descricao = "Usuário promovido para MODERADOR: " + usuario.getNome();
            logAlteracaoService.registrarAlteracao(descricao, moderador, null);
        }

        return usuario;
    }

    public Usuario rebaixarParaCidadao(Long id, Long moderadorId) {
        Usuario usuario = buscarUsuarioPorId(id);

        if (!"USUARIO".equals(usuario.getRole())) {
            usuario.setRole("USUARIO");
            usuarioRepository.save(usuario);

            Usuario moderador = buscarUsuarioPorId(moderadorId);
            String descricao = "Moderador rebaixado para USUARIO: " + usuario.getNome();
            logAlteracaoService.registrarAlteracao(descricao, moderador, null);
        }

        return usuario;
    }

    public ResponseEntity<?> cadastrar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuarioRepository.save(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).build();
}

}
