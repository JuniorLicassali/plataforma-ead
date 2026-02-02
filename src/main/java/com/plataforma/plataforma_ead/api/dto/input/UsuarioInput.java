package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInput {

	@Schema(example = "João Silva")
	@NotBlank
	private String nome;
	
	@Schema(example = "joao.silva@gmail.com")
	@NotBlank
	@Email
	private String email;
	
}