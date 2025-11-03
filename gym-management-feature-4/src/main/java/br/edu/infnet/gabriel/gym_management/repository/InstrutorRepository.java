package br.edu.infnet.gabriel.gym_management.repository;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para a entidade Instrutor.
 * Inclui query methods customizados para buscas específicas.
 */
@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    /**
     * Busca instrutor por CPF
     */
    Optional<Instrutor> findByCpf(String cpf);

    /**
     * Busca instrutor por registro
     */
    Optional<Instrutor> findByRegistro(String registro);

    /**
     * Busca instrutores por especialidade (case insensitive)
     */
    List<Instrutor> findByEspecialidadeIgnoreCase(String especialidade);

    /**
     * Busca instrutores por status
     */
    List<Instrutor> findByStatus(Boolean status);

    /**
     * Busca instrutores por especialidade e status
     */
    List<Instrutor> findByEspecialidadeIgnoreCaseAndStatus(String especialidade, Boolean status);

    /**
     * Busca instrutores por faixa de salário
     */
    List<Instrutor> findBySalarioBetween(Double salarioMin, Double salarioMax);

    /**
     * Busca instrutores por academia
     */
    List<Instrutor> findByAcademiaId(Long academiaId);

    /**
     * Busca instrutores ativos de uma academia específica (JPQL)
     */
    @Query("SELECT i FROM Instrutor i WHERE i.academia.id = :academiaId AND i.status = true")
    List<Instrutor> findInstrutoresAtivosDeAcademia(@Param("academiaId") Long academiaId);

    /**
     * Busca instrutores com salário acima de um valor (JPQL)
     */
    @Query("SELECT i FROM Instrutor i WHERE i.salario > :salarioMinimo ORDER BY i.salario DESC")
    List<Instrutor> findInstrutoresComSalarioAcima(@Param("salarioMinimo") Double salarioMinimo);

    /**
     * Conta instrutores ativos
     */
    Long countByStatus(Boolean status);

    /**
     * Busca instrutores sem academia
     */
    List<Instrutor> findByAcademiaIsNull();

    /**
     * Busca instrutores por cidade do endereço (JPQL com join)
     */
    @Query("SELECT i FROM Instrutor i JOIN i.endereco e WHERE LOWER(e.localidade) = LOWER(:cidade)")
    List<Instrutor> findByEnderecoLocalidade(@Param("cidade") String cidade);
}

