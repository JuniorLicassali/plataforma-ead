package com.plataforma.plataforma_ead.api.dto.input;

import java.math.BigDecimal;

import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagamentoInput {
	
	private MatriculaInput matricula;
	private BigDecimal preco;
	private StatusPagamento statusPagamento;
	private MetodoPagamento metodoPagamento;
	
}