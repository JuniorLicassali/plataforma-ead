package com.plataforma.plataforma_ead.domain.exception;

public class FotoUsuarioNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoUsuarioNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoUsuarioNaoEncontradaException(Long usuarioId) {
		this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d", usuarioId));
	}

}