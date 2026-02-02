package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

	@Schema(example = "1")
    private Long id;

    @Schema(example = "João Silva")
    private String nome;

    @Schema(example = "joao.silva@gmail.com")
    private String email;
	
}
