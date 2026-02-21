package com.plataforma.plataforma_ead.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoDTO {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Formação Java Fullstack")
	private String nome;

	@Schema(example = "Aprenda do zero ao deploy com as tecnologias mais modernas.")
	private String descricao;

	@Schema(example = "497.00")
	private BigDecimal preco;

	@Schema(example = "true")
	private Boolean ativo;

}
