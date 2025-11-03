package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.repository.InstrutorRepository;
import br.edu.infnet.gabriel.gym_management.repository.AcademiaRepository;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Serviço responsável pela gestão de Instrutores.
 * Utiliza JPA Repository para persistência de dados.
 */
@Service
public class InstrutorService implements CrudService<Instrutor, Long> {

    private final InstrutorRepository instrutorRepository;
    private final AcademiaRepository academiaRepository;

    public InstrutorService(InstrutorRepository instrutorRepository, AcademiaRepository academiaRepository) {
        this.instrutorRepository = instrutorRepository;
        this.academiaRepository = academiaRepository;
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
     */
    public Instrutor buscarPorCpf(String cpf) {
        return instrutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("Instrutor com CPF " + cpf + " não encontrado"));
    }

    /**
     * Busca instrutores pela especialidade.
     */
    public List<Instrutor> buscarPorEspecialidade(String especialidade) {
        return instrutorRepository.findByEspecialidadeIgnoreCase(especialidade);
    }

    /**
     * Inativa um instrutor (altera status para false via PATCH).
     */
    public Instrutor inativar(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(false);
        return instrutorRepository.save(instrutor);
    }

    /**
     * Ativa um instrutor (altera status para true).
     */
    public Instrutor ativar(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutor.setStatus(true);
        return instrutorRepository.save(instrutor);
    }

    /**
     * Busca um instrutor pelo registro
     */
    public Instrutor buscarPorRegistro(String registro) {
        return instrutorRepository.findByRegistro(registro)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("Instrutor com registro " + registro + " não encontrado"));
    }

    /**
     * Busca instrutores por status
     */
    public List<Instrutor> buscarPorStatus(Boolean status) {
        return instrutorRepository.findByStatus(status);
    }

    /**
     * Busca instrutores por especialidade e status
     */
    public List<Instrutor> buscarPorEspecialidadeEStatus(String especialidade, Boolean status) {
        return instrutorRepository.findByEspecialidadeIgnoreCaseAndStatus(especialidade, status);
    }

    /**
     * Busca instrutores por faixa de salário
     */
    public List<Instrutor> buscarPorFaixaSalario(Double min, Double max) {
        return instrutorRepository.findBySalarioBetween(min, max);
    }

    /**
     * Busca instrutores com salário acima de um valor
     */
    public List<Instrutor> buscarComSalarioAcima(Double valor) {
        return instrutorRepository.findInstrutoresComSalarioAcima(valor);
    }

    /**
     * Busca instrutores de uma academia
     */
    public List<Instrutor> buscarPorAcademia(Long academiaId) {
        return instrutorRepository.findByAcademiaId(academiaId);
    }

    /**
     * Busca instrutores ativos de uma academia
     */
    public List<Instrutor> buscarInstrutoresAtivosDeAcademia(Long academiaId) {
        return instrutorRepository.findInstrutoresAtivosDeAcademia(academiaId);
    }

    /**
     * Busca instrutores sem academia
     */
    public List<Instrutor> buscarSemAcademia() {
        return instrutorRepository.findByAcademiaIsNull();
    }

    /**
     * Busca instrutores por cidade do endereço
     */
    public List<Instrutor> buscarPorCidade(String cidade) {
        return instrutorRepository.findByEnderecoLocalidade(cidade);
    }

    /**
     * Vincula um instrutor a uma academia
     */
    public Instrutor vincularAcademia(Long instrutorId, Long academiaId) {
        Instrutor instrutor = buscarPorId(instrutorId);
        Academia academia = academiaRepository.findById(academiaId)
            .orElseThrow(() -> new InstrutorInvalidoException("Academia com ID " + academiaId + " não encontrada"));
        instrutor.setAcademia(academia);
        return instrutorRepository.save(instrutor);
    }

    /**
     * Desvincula um instrutor de sua academia
     */
    public Instrutor desvincularAcademia(Long instrutorId) {
        Instrutor instrutor = buscarPorId(instrutorId);
        instrutor.setAcademia(null);
        return instrutorRepository.save(instrutor);
    }

    /**
     * Obtém estatísticas sobre instrutores
     */
    public Map<String, Long> obterEstatisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", instrutorRepository.count());
        stats.put("ativos", instrutorRepository.countByStatus(true));
        stats.put("inativos", instrutorRepository.countByStatus(false));
        return stats;
    }

    /**
     * Valida os dados do instrutor antes de salvar.
     * Validação básica - Bean Validation cuida do resto
     */
    private void validarInstrutor(Instrutor instrutor) {
        if (instrutor == null) {
            throw new InstrutorInvalidoException("Instrutor não pode ser nulo");
        }
    }
}

