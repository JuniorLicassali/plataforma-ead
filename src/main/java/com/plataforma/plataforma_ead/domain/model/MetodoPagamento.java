package com.plataforma.plataforma_ead.domain.model;

import java.util.Arrays;
import java.util.List;

public enum MetodoPagamento {
	
	CARTAO_DE_CREDITO("Cartão de credito"),
	PIX("Pix"),
	BOLETO("Boleto");
	
	private String descricao;
	
	private MetodoPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}
