package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioComSenhaInput extends UsuarioInput {
	
	@NotBlank
	private String senha;
	
}