package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.repository.AcademiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Serviço responsável pela gestão de Academias.
 * Utiliza JPA Repository para persistência de dados.
 * IDs são gerados automaticamente via JPA (IDENTITY strategy).
 */
@Service
public class AcademyService implements CrudService<Academia, Long> {

    private final AcademiaRepository academiaRepository;

    public AcademyService(AcademiaRepository academiaRepository) {
        this.academiaRepository = academiaRepository;
    }

    @Override
    public Academia salvar(Academia academia) {
        return academiaRepository.save(academia);
    }

    @Override
    public Academia buscarPorId(Long id) {
        return academiaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean excluir(Long id) {
        if (academiaRepository.existsById(id)) {
            academiaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Academia> listarTodos() {
        return academiaRepository.findAll();
    }

    /**
     * Busca academias por status
     */
    public List<Academia> buscarPorStatus(Boolean status) {
        return academiaRepository.findByStatusAtivo(status);
    }

    /**
     * Busca academias por nome (contém)
     */
    public List<Academia> buscarPorNome(String nome) {
        return academiaRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca academias ativas com instrutores
     */
    public List<Academia> buscarAcademiasAtivasComInstrutores() {
        return academiaRepository.findAcademiasAtivasComInstrutores();
    }

    /**
     * Busca academias com mínimo de alunos
     */
    public List<Academia> buscarAcademiasComMinimoAlunos(int minAlunos) {
        return academiaRepository.findAcademiasComMinimoAlunos(minAlunos);
    }

    /**
     * Obtém estatísticas sobre academias
     */
    public Map<String, Long> obterEstatisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", academiaRepository.count());
        stats.put("ativas", academiaRepository.countByStatusAtivo(true));
        stats.put("inativas", academiaRepository.countByStatusAtivo(false));
        return stats;
    }
}

