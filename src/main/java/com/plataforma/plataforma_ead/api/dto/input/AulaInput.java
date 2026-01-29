package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AulaInput {

	@NotBlank
	private String titulo;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private Integer ordem;
	
	@NotBlank
	private String urlVideo;
	
	@Valid
	@NotNull
	private ModuloIdInput moduloId;
	
}
