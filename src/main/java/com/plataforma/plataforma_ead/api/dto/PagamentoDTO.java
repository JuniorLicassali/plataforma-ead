package com.plataforma.plataforma_ead.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO {

	private Long id;
	private MatriculaDTO matricula;
	private OffsetDateTime dataCriacao;
	private BigDecimal preco;
	private String statusPagamento;
	private String metodoPagamento;
	private String asaasInvoiceUrl;
	
}
