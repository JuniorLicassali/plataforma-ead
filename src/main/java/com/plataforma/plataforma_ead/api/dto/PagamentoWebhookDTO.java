package com.plataforma.plataforma_ead.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoWebhookDTO {
	
    private Long pagamentoId;
    private String statusPagamento;
    
}