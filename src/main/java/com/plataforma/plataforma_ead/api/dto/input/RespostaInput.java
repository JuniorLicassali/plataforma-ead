package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaInput {

	@NotNull
	private Long perguntaId;
	
	@NotBlank
	private String resposta;
	
}
