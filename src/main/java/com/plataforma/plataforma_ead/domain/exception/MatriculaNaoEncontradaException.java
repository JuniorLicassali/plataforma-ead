package com.plataforma.plataforma_ead.domain.exception;

public class MatriculaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public MatriculaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public MatriculaNaoEncontradaException(Long matriculaId) {
		this(String.format("Não existe uma matrícula com o código %d", matriculaId));
	}

}