package com.plataforma.plataforma_ead.api.dto;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioUsuarioDTO {

	@Schema(example = "1")
    private Long id;
	
	private QuestionarioDTO questionario;
	
	@Schema(example = "2026-02-01T20:34:04Z")
	private OffsetDateTime dataAbertura;
	
	@Schema(example = "2026-02-01T20:39:04Z")
    private OffsetDateTime dataFechamento;
	
	@Schema(example = "true")
    private Boolean finalizado;
	
}
