package com.plataforma.plataforma_ead.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioUsuarioDTO {

	private Long id;
	private QuestionarioDTO questionario;
	private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFechamento;
    private Boolean finalizado;
	
}
