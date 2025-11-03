package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.repository.AlunoRepository;
import br.edu.infnet.gabriel.gym_management.exception.AlunoInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.AlunoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela gestão de Alunos.
 * Utiliza JPA Repository para persistência de dados.
 */
@Service
public class AlunoService implements CrudService<Aluno, Long> {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
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
     *
     * @param cpf O CPF do aluno
     * @return O aluno encontrado
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno buscarPorCpf(String cpf) {
        return alunoRepository.findByCpf(cpf)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com CPF " + cpf + " não encontrado"));
    }

    /**
     * Busca um aluno pela matrícula.
     *
     * @param matricula A matrícula do aluno
     * @return O aluno encontrado
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno buscarPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não encontrado"));
    }

    /**
     * Busca alunos por plano.
     *
     * @param plano O plano de treino
     * @return Lista de alunos com o plano
     */
    public List<Aluno> buscarPorPlano(String plano) {
        return alunoRepository.findByPlanoIgnoreCase(plano);
    }

    /**
     * Inativa um aluno (altera status para false).
     *
     * @param id O ID do aluno
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno inativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(false);
        return alunoRepository.save(aluno);
    }

    /**
     * Ativa um aluno (altera status para true).
     *
     * @param id O ID do aluno
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno ativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(true);
        return alunoRepository.save(aluno);
    }

    /**
     * Valida os dados do aluno antes de salvar.
     *
     * @param aluno O aluno a validar
     * @throws AlunoInvalidoException Se o aluno for inválido
     */
    private void validarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new AlunoInvalidoException("Aluno não pode ser nulo");
        }
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new AlunoInvalidoException("Nome do aluno é obrigatório");
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
            throw new AlunoInvalidoException("CPF do aluno é obrigatório");
        }
        if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty()) {
            throw new AlunoInvalidoException("Email do aluno é obrigatório");
        }
        if (aluno.getMatricula() == null || aluno.getMatricula().trim().isEmpty()) {
            throw new AlunoInvalidoException("Matrícula do aluno é obrigatória");
        }
        if (aluno.getStatus() == null) {
            throw new AlunoInvalidoException("Status do aluno é obrigatório");
        }
    }
}

