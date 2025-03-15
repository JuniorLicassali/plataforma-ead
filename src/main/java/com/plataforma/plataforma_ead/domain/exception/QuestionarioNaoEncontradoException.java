package com.plataforma.plataforma_ead.domain.exception;

public class QuestionarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public QuestionarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public QuestionarioNaoEncontradoException(Long cursoId) {
		this(String.format("Não existe um questionario para o curso com o código %d", cursoId));
	}

}