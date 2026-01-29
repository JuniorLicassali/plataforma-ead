package com.plataforma.plataforma_ead.api.dto.input;

import java.math.BigDecimal;

import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagamentoInput {
	
	@Valid
	@NotNull
	private MatriculaInput matricula;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;
	
	@NotNull
	private MetodoPagamento metodoPagamento;
	
}