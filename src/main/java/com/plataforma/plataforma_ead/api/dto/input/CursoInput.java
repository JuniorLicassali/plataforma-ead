package com.plataforma.plataforma_ead.api.dto.input;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursoInput {

	@Schema(example = "Desenvolvimento Web Fullstack")
	@NotBlank
	private String nome;
	
	@Schema(example = "Aprenda do zero ao avançado com as tecnologias mais modernas do mercado.")
	@NotBlank
	private String descricao;
	
	@Schema(example = "299.90")
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;
	
}
