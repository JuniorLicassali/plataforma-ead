package com.plataforma.plataforma_ead.api.dto;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatriculaDTO {
	
	@Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long usuarioId;

    @Schema(example = "1")
    private Long cursoId;

    @Schema(example = "2026-02-01T20:34:04Z")
    private OffsetDateTime dataMatricula;

    @Schema(example = "PAGAMENTO_CONFIRMADO")
    private String statusMatricula;
    
}