package br.edu.infnet.gabriel.gym_management.exception;

/**
 * Exceção lançada quando um Aluno não é encontrado.
 */
public class AlunoNaoEncontradoException extends RuntimeException {

    public AlunoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public AlunoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

