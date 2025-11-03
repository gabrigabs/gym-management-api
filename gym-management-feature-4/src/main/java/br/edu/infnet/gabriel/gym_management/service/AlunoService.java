package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.repository.AlunoRepository;
import br.edu.infnet.gabriel.gym_management.repository.AcademiaRepository;
import br.edu.infnet.gabriel.gym_management.exception.AlunoInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.AlunoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Serviço responsável pela gestão de Alunos.
 * Utiliza JPA Repository para persistência de dados.
 */
@Service
public class AlunoService implements CrudService<Aluno, Long> {

    private final AlunoRepository alunoRepository;
    private final AcademiaRepository academiaRepository;

    public AlunoService(AlunoRepository alunoRepository, AcademiaRepository academiaRepository) {
        this.alunoRepository = alunoRepository;
        this.academiaRepository = academiaRepository;
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        validarAluno(aluno);
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID " + id + " não encontrado"));
    }

    @Override
    public Boolean excluir(Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    /**
     * Busca um aluno pelo CPF.
     */
    public Aluno buscarPorCpf(String cpf) {
        return alunoRepository.findByCpf(cpf)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com CPF " + cpf + " não encontrado"));
    }

    /**
     * Busca um aluno pela matrícula.
     */
    public Aluno buscarPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não encontrado"));
    }

    /**
     * Busca alunos por plano.
     */
    public List<Aluno> buscarPorPlano(String plano) {
        return alunoRepository.findByPlanoIgnoreCase(plano);
    }

    /**
     * Inativa um aluno (altera status para false).
     */
    public Aluno inativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(false);
        return alunoRepository.save(aluno);
    }

    /**
     * Ativa um aluno (altera status para true).
     */
    public Aluno ativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(true);
        return alunoRepository.save(aluno);
    }

    /**
     * Busca alunos por status
     */
    public List<Aluno> buscarPorStatus(Boolean status) {
        return alunoRepository.findByStatus(status);
    }

    /**
     * Busca alunos por plano e status
     */
    public List<Aluno> buscarPorPlanoEStatus(String plano, Boolean status) {
        return alunoRepository.findByPlanoIgnoreCaseAndStatus(plano, status);
    }

    /**
     * Busca alunos de uma academia
     */
    public List<Aluno> buscarPorAcademia(Long academiaId) {
        return alunoRepository.findByAcademiaId(academiaId);
    }

    /**
     * Busca alunos ativos de uma academia
     */
    public List<Aluno> buscarAlunosAtivosDeAcademia(Long academiaId) {
        return alunoRepository.findAlunosAtivosDeAcademia(academiaId);
    }

    /**
     * Busca alunos por período de início
     */
    public List<Aluno> buscarPorPeriodo(String dataInicio, String dataFim) {
        return alunoRepository.findByDataInicioBetween(dataInicio, dataFim);
    }

    /**
     * Busca alunos sem academia
     */
    public List<Aluno> buscarSemAcademia() {
        return alunoRepository.findByAcademiaIsNull();
    }

    /**
     * Vincula um aluno a uma academia
     */
    public Aluno vincularAcademia(Long alunoId, Long academiaId) {
        Aluno aluno = buscarPorId(alunoId);
        Academia academia = academiaRepository.findById(academiaId)
            .orElseThrow(() -> new AlunoInvalidoException("Academia com ID " + academiaId + " não encontrada"));
        aluno.setAcademia(academia);
        return alunoRepository.save(aluno);
    }

    /**
     * Desvincula um aluno de sua academia
     */
    public Aluno desvincularAcademia(Long alunoId) {
        Aluno aluno = buscarPorId(alunoId);
        aluno.setAcademia(null);
        return alunoRepository.save(aluno);
    }

    /**
     * Obtém estatísticas sobre alunos
     */
    public Map<String, Long> obterEstatisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", alunoRepository.count());
        stats.put("ativos", alunoRepository.countByStatus(true));
        stats.put("inativos", alunoRepository.countByStatus(false));
        return stats;
    }

    /**
     * Valida os dados do aluno antes de salvar.
     * Validação básica - Bean Validation cuida do resto
     */
    private void validarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new AlunoInvalidoException("Aluno não pode ser nulo");
        }
    }
}

