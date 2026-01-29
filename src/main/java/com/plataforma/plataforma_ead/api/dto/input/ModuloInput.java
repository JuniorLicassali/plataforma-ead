package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuloInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@NotNull
    private Integer ordem;
	
}
