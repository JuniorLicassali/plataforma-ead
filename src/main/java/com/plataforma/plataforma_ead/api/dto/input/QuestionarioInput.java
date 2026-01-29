package com.plataforma.plataforma_ead.api.dto.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionarioInput {

	@NotBlank
	private String descricao;
	
	@Valid
	//@NotNull
	private List<PerguntaInput> perguntas;
	
}
