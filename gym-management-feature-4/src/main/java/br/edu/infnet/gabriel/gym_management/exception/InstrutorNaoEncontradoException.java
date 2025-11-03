package br.edu.infnet.gabriel.gym_management.exception;

/**
 * Exceção lançada quando um Instrutor não é encontrado.
 * Por exemplo: ao tentar buscar um instrutor com ID inexistente.
 */
public class InstrutorNaoEncontradoException extends RuntimeException {

    public InstrutorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public InstrutorNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

