package br.edu.infnet.gabriel.gym_management.repository;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para a entidade Academia.
 * Inclui query methods customizados para buscas específicas.
 */
@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Long> {

    /**
     * Busca academia por CNPJ
     */
    Optional<Academia> findByCnpj(String cnpj);

    /**
     * Busca academias por status
     */
    List<Academia> findByStatusAtivo(Boolean statusAtivo);

    /**
     * Busca academias por nome (case insensitive, contains)
     */
    List<Academia> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca academias ativas com instrutores (JPQL)
     */
    @Query("SELECT DISTINCT a FROM Academia a LEFT JOIN FETCH a.instrutores WHERE a.statusAtivo = true")
    List<Academia> findAcademiasAtivasComInstrutores();

    /**
     * Busca academias com pelo menos X alunos (JPQL com subconsulta)
     */
    @Query("SELECT a FROM Academia a WHERE SIZE(a.alunos) >= :minAlunos")
    List<Academia> findAcademiasComMinimoAlunos(@Param("minAlunos") int minAlunos);

    /**
     * Conta academias ativas
     */
    Long countByStatusAtivo(Boolean statusAtivo);
}

