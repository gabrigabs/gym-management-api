package br.edu.infnet.gabriel.gym_management.repository;

import br.edu.infnet.gabriel.gym_management.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Endereco.
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}

