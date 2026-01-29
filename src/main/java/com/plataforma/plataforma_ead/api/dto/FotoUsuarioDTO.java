package com.plataforma.plataforma_ead.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoUsuarioDTO {
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;

}