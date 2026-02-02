package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaDTO {

	@Schema(example = "1")
    private Long id;

    @Schema(example = "Qual é a principal vantagem de usar Spring Boot?")
    private String enunciado;
	
	private List<PerguntaOpcaoDTO> opcoes;
	
}
