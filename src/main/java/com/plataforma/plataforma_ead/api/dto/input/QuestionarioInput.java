package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionarioInput {

	@Schema(example = "Avaliação de Conhecimentos Básicos")
	@NotBlank
	private String descricao;
	
	//@Valid
	//@NotNull
	//private List<PerguntaInput> perguntas;
	
}
