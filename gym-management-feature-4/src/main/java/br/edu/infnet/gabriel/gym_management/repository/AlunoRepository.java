package br.edu.infnet.gabriel.gym_management.repository;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para a entidade Aluno.
 * Inclui query methods customizados para buscas específicas.
 */
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    /**
     * Busca aluno por CPF
     */
    Optional<Aluno> findByCpf(String cpf);

    /**
     * Busca aluno por matrícula
     */
    Optional<Aluno> findByMatricula(String matricula);

    /**
     * Busca alunos por plano (case insensitive)
     */
    List<Aluno> findByPlanoIgnoreCase(String plano);

    /**
     * Busca alunos por status
     */
    List<Aluno> findByStatus(Boolean status);

    /**
     * Busca alunos por plano e status
     */
    List<Aluno> findByPlanoIgnoreCaseAndStatus(String plano, Boolean status);

    /**
     * Busca alunos por academia (usando relacionamento)
     */
    List<Aluno> findByAcademiaId(Long academiaId);

    /**
     * Busca alunos ativos de uma academia específica (JPQL)
     */
    @Query("SELECT a FROM Aluno a WHERE a.academia.id = :academiaId AND a.status = true")
    List<Aluno> findAlunosAtivosDeAcademia(@Param("academiaId") Long academiaId);

    /**
     * Busca alunos por intervalo de data de início (JPQL)
     */
    @Query("SELECT a FROM Aluno a WHERE a.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<Aluno> findByDataInicioBetween(@Param("dataInicio") String dataInicio, @Param("dataFim") String dataFim);

    /**
     * Conta alunos ativos
     */
    Long countByStatus(Boolean status);

    /**
     * Busca alunos sem academia
     */
    List<Aluno> findByAcademiaIsNull();
}

