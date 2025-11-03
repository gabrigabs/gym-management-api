package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Endereco;
import br.edu.infnet.gabriel.gym_management.exception.EnderecoInvalidoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço responsável pela gestão de Endereços.
 * Utiliza ConcurrentHashMap para armazenamento em memória com thread-safety.
 */
@Service
public class EnderecoService implements CrudService<Endereco, Integer> {

    private final ConcurrentHashMap<Integer, Endereco> repositorio = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Endereco salvar(Endereco endereco) {
        validarEndereco(endereco);
        if (endereco.getId() == null) {
            endereco.setId(idGenerator.getAndIncrement());
        }
        repositorio.put(endereco.getId(), endereco);
        return endereco;
    }

    @Override
    public Endereco buscarPorId(Integer id) {
        return repositorio.get(id);
    }

    @Override
    public Boolean excluir(Integer id) {
        return repositorio.remove(id) != null;
    }

    @Override
    public List<Endereco> listarTodos() {
        return List.copyOf(repositorio.values());
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

