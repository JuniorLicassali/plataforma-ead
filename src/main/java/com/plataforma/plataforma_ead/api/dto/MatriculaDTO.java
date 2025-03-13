package com.plataforma.plataforma_ead.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatriculaDTO {
	
    private Long id;
    private Long usuarioId;
    private Long cursoId;
    private OffsetDateTime dataMatricula;
    private String statusMatricula;
    
}