package br.edu.infnet.gabriel.gym_management.exception;

/**
 * Exceção lançada quando um Aluno é inválido.
 */
public class AlunoInvalidoException extends RuntimeException {

    public AlunoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public AlunoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

