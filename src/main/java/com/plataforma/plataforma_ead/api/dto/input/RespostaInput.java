package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaInput {

	@Schema(example = "1")
	@NotNull
	private Long perguntaId;
	
	@Schema(example = "O padrão DTO serve para isolar a camada de domínio da camada de apresentação.")
	@NotBlank
	private String resposta;
	
}
