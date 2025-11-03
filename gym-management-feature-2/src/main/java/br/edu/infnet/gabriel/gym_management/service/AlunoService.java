package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.exception.AlunoInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.AlunoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço responsável pela gestão de Alunos.
 * Utiliza ConcurrentHashMap para armazenamento em memória com thread-safety.
 */
@Service
public class AlunoService implements CrudService<Aluno, Integer> {

    private final ConcurrentHashMap<Integer, Aluno> repositorio = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Aluno salvar(Aluno aluno) {
        validarAluno(aluno);
        if (aluno.getId() == null) {
            aluno.setId(idGenerator.getAndIncrement());
        }
        repositorio.put(aluno.getId(), aluno);
        return aluno;
    }

    @Override
    public Aluno buscarPorId(Integer id) {
        Aluno aluno = repositorio.get(id);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException("Aluno com ID " + id + " não encontrado");
        }
        return aluno;
    }

    @Override
    public Boolean excluir(Integer id) {
        Aluno removido = repositorio.remove(id);
        return removido != null;
    }

    @Override
    public List<Aluno> listarTodos() {
        return List.copyOf(repositorio.values());
    }

    /**
     * Busca um aluno pelo CPF.
     *
     * @param cpf O CPF do aluno
     * @return O aluno encontrado
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno buscarPorCpf(String cpf) {
        return repositorio.values().stream()
                .filter(a -> a.getCpf() != null && a.getCpf().equals(cpf))
                .findFirst()
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
        return repositorio.values().stream()
                .filter(a -> a.getMatricula() != null && a.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não encontrado"));
    }

    /**
     * Busca alunos por plano.
     *
     * @param plano O plano de treino
     * @return Lista de alunos com o plano
     */
    public List<Aluno> buscarPorPlano(String plano) {
        return repositorio.values().stream()
                .filter(a -> a.getPlano() != null && a.getPlano().equalsIgnoreCase(plano))
                .toList();
    }

    /**
     * Inativa um aluno (altera status para false).
     *
     * @param id O ID do aluno
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno inativar(Integer id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(false);
        repositorio.put(id, aluno);
        return aluno;
    }

    /**
     * Ativa um aluno (altera status para true).
     *
     * @param id O ID do aluno
     * @throws AlunoNaoEncontradoException Se não encontrar
     */
    public Aluno ativar(Integer id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatus(true);
        repositorio.put(id, aluno);
        return aluno;
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

