package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.repository.InstrutorRepository;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela gestão de Instrutores.
 * Utiliza JPA Repository para persistência de dados.
 */
@Service
public class InstrutorService implements CrudService<Instrutor, Long> {

    private final InstrutorRepository instrutorRepository;

    public InstrutorService(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    @Override
    public Instrutor salvar(Instrutor instrutor) {
        validarInstrutor(instrutor);
        return instrutorRepository.save(instrutor);
    }

    @Override
    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("Instrutor com ID " + id + " não encontrado"));
    }

    @Override
    public Boolean excluir(Long id) {
        if (instrutorRepository.existsById(id)) {
            instrutorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Instrutor> listarTodos() {
        return instrutorRepository.findAll();
    }

    /**
     * Busca um instrutor pelo CPF.
     *
     * @param cpf O CPF do instrutor
     * @return O instrutor encontrado
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor buscarPorCpf(String cpf) {
        return instrutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("Instrutor com CPF " + cpf + " não encontrado"));
    }

    /**
     * Busca instrutores pela especialidade.
     *
     * @param especialidade A especialidade
     * @return Lista de instrutores com a especialidade
     */
    public List<Instrutor> buscarPorEspecialidade(String especialidade) {
        return instrutorRepository.findByEspecialidadeIgnoreCase(especialidade);
    }

    /**
     * Inativa um instrutor (altera status para false via PATCH).
     *
     * @param id O ID do instrutor
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor inativar(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(false);
        return instrutorRepository.save(instrutor);
    }

    /**
     * Ativa um instrutor (altera status para true).
     *
     * @param id O ID do instrutor
     * @throws InstrutorNaoEncontradoException Se não encontrar
     */
    public Instrutor ativar(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(true);
        return instrutorRepository.save(instrutor);
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

