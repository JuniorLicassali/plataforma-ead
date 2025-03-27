package com.plataforma.plataforma_ead.api.dto.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AulaInput {

	private String titulo;
	private String descricao;
	private Integer ordem;
	private String urlVideo;
	private ModuloIdInput moduloId;
	
}
