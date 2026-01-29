package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdInput {

	@NotNull
	private Long id;
	
}
