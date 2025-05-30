package br.mackenzie.bahtche.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.mackenzie.bahtche.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
