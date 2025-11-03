package br.edu.infnet.gabriel.gym_management.repository;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade Academia.
 */
@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Long> {
    Optional<Academia> findByCnpj(String cnpj);
}

