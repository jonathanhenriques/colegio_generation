package com.jonathan.colegiogeneration.api.exception;

public class AlunoNaoEncontradoException extends RuntimeException{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AlunoNaoEncontradoException(Object message) {
        super("Aluno não encontrada! | " + message);
    }

    public AlunoNaoEncontradoException() {
        super("Aluno não encontrada!");
    }
}
