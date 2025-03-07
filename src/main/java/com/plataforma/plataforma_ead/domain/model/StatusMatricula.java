package com.plataforma.plataforma_ead.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusMatricula {

	PAGAMENTO_PENDENTE("Pagamento pendente"),
	PAGAMENTO_CONFIRMADO("Pagamento confirmado"),
	EXPIRADA("Matrícula expirada"),
	CANCELADA("Matrícula cancelada");
	
	private String descricao;
	private List<StatusMatricula> statusAnteriores;
	
	private StatusMatricula(String descricao, StatusMatricula... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusMatricula novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
	public boolean podeAlterarPara(StatusMatricula novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}
	
}
