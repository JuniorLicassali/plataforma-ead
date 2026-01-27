package com.plataforma.plataforma_ead.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPagamento {
	
	PAGAMENTO_PENDENTE("Pagamento pendente"),
	PAGAMENTO_CONCLUIDO("Pagamento concluído", PAGAMENTO_PENDENTE),
	FALHOU("Falhou", PAGAMENTO_PENDENTE),
	REEMBOLSADO("Reembolsado", PAGAMENTO_CONCLUIDO);
	
	private String descricao;
	private List<StatusPagamento> statusAnteriores;
	
	private StatusPagamento(String descricao, StatusPagamento... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPagamento novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
	public boolean podeAlterarPara(StatusPagamento novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}
	
}
