package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuloDTO {

	private Long id;
	private String nome;
	private String descricao;
	private List<AulaDTO> aulas;
	
}
