package com.plataforma.plataforma_ead.api.dto.input;

import java.math.BigDecimal;

import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
	@Schema(example = "199.90")
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;
	
	@Schema(example = "CARTAO_DE_CREDITO")
	@NotNull
	private MetodoPagamento metodoPagamento;
	
}