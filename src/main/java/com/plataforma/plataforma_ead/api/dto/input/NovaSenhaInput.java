package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NovaSenhaInput {
	
	@Schema(example = "joao.ger@hotmail.com.br")
	@NotBlank
	private String email;
	
	@Schema(example = "195111")
	@NotBlank
    private String codigo;
	
	@Schema(example = "NovaSenha#2026")
	@NotBlank
	private String novaSenha;
	
}