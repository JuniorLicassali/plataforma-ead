package com.plataforma.plataforma_ead.domain.exception;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PagamentoNaoEncontradoException(Long pagamentoId) {
		this(String.format("Não existe um pagamento com o código %d", pagamentoId));
	}

}