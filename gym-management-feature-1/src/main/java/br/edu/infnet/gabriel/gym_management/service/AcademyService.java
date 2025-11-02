package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço responsável pela gestão de Academias.
 * Utiliza ConcurrentHashMap para armazenamento em memória com thread-safety.
 * IDs são gerados automaticamente via AtomicInteger.
 */
@Service
public class AcademyService implements CrudService<Academia, Integer> {

    private final ConcurrentHashMap<Integer, Academia> repositorio = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    /**
     * Salva uma nova academia ou atualiza uma existente.
     * Se o ID é nulo, um novo ID é gerado.
     *
     * @param academia A academia a ser salva
     * @return A academia salva com ID atribuído
     */
    @Override
    public Academia salvar(Academia academia) {
        if (academia.getId() == null) {
            academia.setId(idGenerator.getAndIncrement());
        }
        repositorio.put(academia.getId(), academia);
        return academia;
    }

    /**
     * Busca uma academia pelo ID.
     *
     * @param id O ID da academia
     * @return A academia encontrada, ou null se não existir
     */
    @Override
    public Academia buscarPorId(Integer id) {
        return repositorio.get(id);
    }

    /**
     * Remove uma academia pelo ID.
     *
     * @param id O ID da academia a ser removida
     * @return true se a academia foi removida, false se não existia
     */
    @Override
    public Boolean excluir(Integer id) {
        return repositorio.remove(id) != null;
    }

    /**
     * Lista todas as academias armazenadas.
     *
     * @return Uma lista contendo todas as academias
     */
    @Override
    public List<Academia> listarTodos() {
        return List.copyOf(repositorio.values());
    }
}

