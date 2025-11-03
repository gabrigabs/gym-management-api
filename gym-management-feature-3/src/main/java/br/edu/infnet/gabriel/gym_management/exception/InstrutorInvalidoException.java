package br.edu.infnet.gabriel.gym_management.exception;

/**
 * Exceção lançada quando um Instrutor é inválido.
 * Por exemplo: dados obrigatórios faltando, valores inválidos, etc.
 */
public class InstrutorInvalidoException extends RuntimeException {

    public InstrutorInvalidoException(String mensagem) {
        super(mensagem);
    }

    public InstrutorInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

