package com.plataforma.plataforma_ead.api.dto.input;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursoInput {

	private String nome;
	private String descricao;
	private BigDecimal preco;
	
}
