package com.plataforma.plataforma_ead.api.dto.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionarioInput {

	private String descricao;
	private List<PerguntaInput> perguntas;
	
}
