package com.plataforma.plataforma_ead.api.openapi.controller;

import org.springframework.web.multipart.MultipartFile;

import com.plataforma.plataforma_ead.api.dto.AulaDTO;
import com.plataforma.plataforma_ead.api.dto.input.AulaInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Aulas")
public interface AulaControllerOpenApi {
	
	@Operation(summary = "Adiciona uma aula", responses = {
			@ApiResponse(responseCode = "201", description = "Aula cadastrada"),
	})
	public AulaDTO adicionar(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId,  
			@Parameter(description = "ID de um módulo", example = "1", required = true) Long moduloId, 
			@Parameter(description = "Dados de entrada da aula (JSON)", required = true, schema = @Schema(implementation = AulaInput.class))  AulaInput aulaInput, 
			@Parameter(description = "Arquivo de vídeo da aula (.mp4, .mov, etc.)", required = true, schema = @Schema(type = "string", format = "binary")) MultipartFile video) throws Exception;
	
	@Operation(summary = "Exclui uma aula por ID", responses = {
			@ApiResponse(responseCode = "204")
	})
	public void excluir(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, 
			@Parameter(description = "ID de um módulo", example = "1", required = true) Long moduloId, 
			@Parameter(description = "ID de uma aula", example = "1", required = true) Long aulaId);
	
}
