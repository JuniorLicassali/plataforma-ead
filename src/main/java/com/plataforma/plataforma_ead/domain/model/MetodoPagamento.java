package com.plataforma.plataforma_ead.domain.model;

import java.util.Arrays;
import java.util.List;

public enum MetodoPagamento {
	
	CARTAO_DE_CREDITO("Cartão de credito"),
	PIX("Pix"),
	BOLETO("Boleto");
	
	private String descricao;
	private List<MetodoPagamento> statusAnteriores;
	
	private MetodoPagamento(String descricao, MetodoPagamento... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(MetodoPagamento novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
	public boolean podeAlterarPara(MetodoPagamento novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}
	
}
