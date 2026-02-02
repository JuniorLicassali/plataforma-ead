package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaOpcaoInput {
	
	@Schema(example = "O padrão DTO serve para transportar dados entre diferentes camadas do sistema.")
    @NotBlank
    private String texto;
	
    @Schema(example = "true")
    @NotNull
    private Boolean isCorreta;

}
