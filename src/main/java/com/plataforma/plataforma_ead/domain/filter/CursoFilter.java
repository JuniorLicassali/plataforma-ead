package com.plataforma.plataforma_ead.domain.filter;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoFilter {
	
	private String nome;
	
	private BigDecimal precoMin;
	
	private BigDecimal precoMax;
	
	private Boolean ativo;

}
