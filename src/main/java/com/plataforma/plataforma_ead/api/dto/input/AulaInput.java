package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AulaInput {

	@Schema(example = "Introdução ao Spring Security")
    @NotBlank
    private String titulo;
    
    @Schema(example = "Nesta aula configuraremos os filtros de autenticação.")
    @NotBlank
    private String descricao;
    
    @Schema(example = "1")
    @NotNull
    private Integer ordem;
	
	@Valid
	@NotNull
	private ModuloIdInput moduloId;
	
}
