package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço responsável pela gestão de Instrutores.
 * Utiliza ConcurrentHashMap para armazenamento em memória com thread-safety.
 */
@Service
public class InstrutorService implements CrudService<Instrutor, Integer> {

    private final ConcurrentHashMap<Integer, Instrutor> repositorio = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Instrutor salvar(Instrutor instrutor) {
        validarInstrutor(instrutor);
        if (instrutor.getId() == null) {
            instrutor.setId(idGenerator.getAndIncrement());
        }
        repositorio.put(instrutor.getId(), instrutor);
        return instrutor;
    }

    @Override
    public Instrutor buscarPorId(Integer id) {
        Instrutor instrutor = repositorio.get(id);
        if (instrutor == null) {
            throw new InstrutorNaoEncontradoException("Instrutor com ID " + id + " não encontrado");
        }
        return instrutor;
    }

    @Override
    public Boolean excluir(Integer id) {
        Instrutor removido = repositorio.remove(id);
        return removido != null;
    }

    @Override
    public List<Instrutor> listarTodos() {
        return List.copyOf(repositorio.values());
    }

    /**
     * Busca um instrutor pelo CPF.
     *
     * @param cpf O CPF do instrutor
     * @return O instrutor encontrado
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor buscarPorCpf(String cpf) {
        return repositorio.values().stream()
                .filter(i -> i.getCpf() != null && i.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new InstrutorNaoEncontradoException("Instrutor com CPF " + cpf + " não encontrado"));
    }

    /**
     * Busca instrutores pela especialidade.
     *
     * @param especialidade A especialidade
     * @return Lista de instrutores com a especialidade
     */
    public List<Instrutor> buscarPorEspecialidade(String especialidade) {
        return repositorio.values().stream()
                .filter(i -> i.getEspecialidade() != null && i.getEspecialidade().equalsIgnoreCase(especialidade))
                .toList();
    }

    /**
     * Inativa um instrutor (altera status para false via PATCH).
     *
     * @param id O ID do instrutor
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor inativar(Integer id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(false);
        repositorio.put(id, instrutor);
        return instrutor;
    }

    /**
     * Ativa um instrutor (altera status para true).
     *
     * @param id O ID do instrutor
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor ativar(Integer id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(true);
        repositorio.put(id, instrutor);
        return instrutor;
    }

    /**
     * Valida os dados do instrutor antes de salvar.
     *
     * @param instrutor O instrutor a validar
     * @throws InstrutorInvalidoException Se o instrutor for inválido
     */
    private void validarInstrutor(Instrutor instrutor) {
        if (instrutor == null) {
            throw new InstrutorInvalidoException("Instrutor não pode ser nulo");
        }
        if (instrutor.getNome() == null || instrutor.getNome().trim().isEmpty()) {
            throw new InstrutorInvalidoException("Nome do instrutor é obrigatório");
        }
        if (instrutor.getCpf() == null || instrutor.getCpf().trim().isEmpty()) {
            throw new InstrutorInvalidoException("CPF do instrutor é obrigatório");
        }
        if (instrutor.getEmail() == null || instrutor.getEmail().trim().isEmpty()) {
            throw new InstrutorInvalidoException("Email do instrutor é obrigatório");
        }
        if (instrutor.getRegistro() == null || instrutor.getRegistro().trim().isEmpty()) {
            throw new InstrutorInvalidoException("Registro do instrutor é obrigatório");
        }
        if (instrutor.getSalario() == null || instrutor.getSalario() <= 0) {
            throw new InstrutorInvalidoException("Salário deve ser maior que zero");
        }
        if (instrutor.getStatus() == null) {
            throw new InstrutorInvalidoException("Status do instrutor é obrigatório");
        }
    }
}

