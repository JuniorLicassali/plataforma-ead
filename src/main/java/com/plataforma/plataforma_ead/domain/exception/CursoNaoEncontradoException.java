package com.plataforma.plataforma_ead.domain.exception;

public class CursoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CursoNaoEncontradoException(Long cursoId) {
		this(String.format("Não existe um curso com o código %d", cursoId));
	}

}