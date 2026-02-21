package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoDTO {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "CONSULTAR_CURSO")
	private String nome;

	@Schema(example = "Permite consultar cursos")
	private String descricao;
	
}