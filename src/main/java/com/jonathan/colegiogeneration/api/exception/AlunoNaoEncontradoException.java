package com.jonathan.colegiogeneration.api.exception;

public class AlunoNaoEncontradoException extends RuntimeException{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AlunoNaoEncontradoException(Object message) {
        super("Aluno não encontrado! | " + message);
    }

    public AlunoNaoEncontradoException() {
        super("Aluno não encontrado!");
    }
}
