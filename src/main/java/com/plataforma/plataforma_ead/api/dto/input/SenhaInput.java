package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SenhaInput {
	
	@Schema(example = "Senha@123")
	@NotBlank
	private String senhaAtual;
	
	@Schema(example = "NovaSenha#2024")
	@NotBlank
	private String novaSenha;
	
}