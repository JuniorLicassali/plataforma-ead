package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioDTO {
	
	@Schema(example = "1")
    private Long id;

    @Schema(example = "Avaliação Final de Spring Boot")
    private String descricao;

    @Schema(example = "true")
    private Boolean ativo;
	
	private List<PerguntaDTO> perguntas;

}
