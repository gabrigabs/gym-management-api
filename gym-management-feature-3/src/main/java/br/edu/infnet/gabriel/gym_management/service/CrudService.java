package br.edu.infnet.gabriel.gym_management.service;

import java.util.List;

/**
 * Interface genérica que define operações CRUD básicas para qualquer entidade.
 *
 * @param <T> O tipo da entidade
 * @param <ID> O tipo do identificador único
 */
public interface CrudService<T, ID> {

    /**
     * Salva uma nova entidade ou atualiza uma existente.
     *
     * @param entidade A entidade a ser salva
     * @return A entidade salva com ID atribuído
     */
    T salvar(T entidade);

    /**
     * Busca uma entidade pelo seu identificador.
     *
     * @param id O identificador da entidade
     * @return A entidade encontrada, ou null se não existir
     */
    T buscarPorId(ID id);

    /**
     * Remove uma entidade pelo seu identificador.
     *
     * @param id O identificador da entidade a ser removida
     * @return true se a entidade foi removida, false se não existia
     */
    Boolean excluir(ID id);

    /**
     * Lista todas as entidades armazenadas.
     *
     * @return Uma lista contendo todas as entidades
     */
    List<T> listarTodos();
}

