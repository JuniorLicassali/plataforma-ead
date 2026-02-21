package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoDTO {
	@Schema(example = "1")
	private Long id;

	@Schema(example = "Gerente")
	private String nome;
}