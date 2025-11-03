package br.edu.infnet.gabriel.gym_management.exception;

/**
 * Exceção lançada quando um Endereço é inválido.
 */
public class EnderecoInvalidoException extends RuntimeException {

    public EnderecoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public EnderecoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

