package com.plataforma.plataforma_ead.api.dto.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaInput {

	private String enunciado;
	private List<PerguntaOpcaoInput> opcoes;
	
}
