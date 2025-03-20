package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioDTO {
	
	private Long id; 
	private String descricao;
	private Boolean ativo;
	private CursoDTO curso;
	private List<PerguntaDTO> perguntas;

}
