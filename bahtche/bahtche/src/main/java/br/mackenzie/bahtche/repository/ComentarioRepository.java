package br.mackenzie.bahtche.repository;

import br.mackenzie.bahtche.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProblemaId(Long problemaId);
}

