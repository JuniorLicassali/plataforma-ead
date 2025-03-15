package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaDTO {

	private Long id;
	private String enunciado;
	private List<PerguntaOpcaoDTO> opcoes;
	
}
