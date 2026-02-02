package com.plataforma.plataforma_ead.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO {

	@Schema(example = "1")
    private Long id;
	
	private MatriculaDTO matricula;
	
	@Schema(example = "2026-02-01T20:34:04Z")
	private OffsetDateTime dataCriacao;
	
	@Schema(example = "199.90")
    private BigDecimal preco;
	
	@Schema(example = "PAGAMENTO_CONCLUIDO")
	private String statusPagamento;
	
	@Schema(example = "CARTAO_CREDITO")
    private String metodoPagamento;
	
	@Schema(example = "https://sandbox.asaas.com/i/t5t0emaxk8bnfidn")
    private String asaasInvoiceUrl;
	
}
