package br.mackenzie.bahtche.controller;

import br.mackenzie.bahtche.dto.LoginRequest;
import br.mackenzie.bahtche.dto.UsuarioDTO;
import br.mackenzie.bahtche.model.Usuario;
import br.mackenzie.bahtche.repository.UsuarioRepository;
import br.mackenzie.bahtche.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setAtivo(usuarioAtualizado.getAtivo());
        usuario.setRole(usuarioAtualizado.getRole());

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.autenticarUsuario(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
        }
    }

    @PutMapping("/{id}/promover")
    public Usuario promoverUsuario(@PathVariable Long id, @RequestParam Long moderadorId) {
        return usuarioService.promoverParaModerador(id, moderadorId);
    }
    
    @PutMapping("/{id}/rebaixar")
    public Usuario rebaixarUsuario(@PathVariable Long id, @RequestParam Long moderadorId) {
        return usuarioService.rebaixarParaCidadao(id, moderadorId);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Usuario> alterarStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> payload, @RequestParam Long moderadorId) {

        boolean novoStatus = payload.getOrDefault("ativo", true);
        Usuario atualizado = usuarioService.alterarStatus(id, novoStatus, moderadorId);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }
    
}
