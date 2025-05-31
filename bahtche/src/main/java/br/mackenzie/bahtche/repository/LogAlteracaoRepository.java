package br.mackenzie.bahtche.repository;

import br.mackenzie.bahtche.model.LogAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAlteracaoRepository extends JpaRepository<LogAlteracao, Long> {
    LogAlteracao findTopByProblemaIdOrderByDataAlteracaoDesc(Long problemaId);
}