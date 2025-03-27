package com.plataforma.plataforma_ead.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDTO {
	
	private Long id;
	private String titulo;
	private String descricao;
	private Integer ordem;
	private String urlVideo;
	
}
