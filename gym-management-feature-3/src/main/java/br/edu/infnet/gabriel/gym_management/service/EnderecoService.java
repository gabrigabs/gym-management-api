package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Endereco;
import br.edu.infnet.gabriel.gym_management.repository.EnderecoRepository;
import br.edu.infnet.gabriel.gym_management.exception.EnderecoInvalidoException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela gestão de Endereços.
 * Utiliza JPA Repository para persistência de dados.
 */
@Service
public class EnderecoService implements CrudService<Endereco, Long> {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        validarEndereco(endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean excluir(Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    /**
     * Valida os dados do endereço antes de salvar.
     *
     * @param endereco O endereço a validar
     * @throws EnderecoInvalidoException Se o endereço for inválido
     */
    private void validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new EnderecoInvalidoException("Endereço não pode ser nulo");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
            throw new EnderecoInvalidoException("CEP é obrigatório");
        }
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
            throw new EnderecoInvalidoException("Logradouro é obrigatório");
        }
    }
}

