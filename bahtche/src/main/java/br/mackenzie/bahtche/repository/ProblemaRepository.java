package br.mackenzie.bahtche.repository;

import br.mackenzie.bahtche.model.Problema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemaRepository extends JpaRepository<Problema, Long> {
}