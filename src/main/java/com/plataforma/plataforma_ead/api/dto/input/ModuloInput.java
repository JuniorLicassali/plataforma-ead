package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuloInput {

	@Schema(example = "Fundamentos de Spring Boot")
	@NotBlank
	private String nome;
	
	@Schema(example = "Neste módulo aprenderemos a configurar o ambiente e criar o primeiro projeto.")
	@NotBlank
	private String descricao;
	
	@Schema(example = "1")
	@NotNull
    private Integer ordem;
	
}
