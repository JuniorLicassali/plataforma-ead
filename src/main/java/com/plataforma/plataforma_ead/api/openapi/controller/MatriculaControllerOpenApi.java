package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import com.plataforma.plataforma_ead.api.dto.MatriculaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Matrículas")
public interface MatriculaControllerOpenApi {
	
	@Operation(summary = "Lista todos as matrículas")
	public List<MatriculaDTO> listar();
	
	@Operation(summary = "Busca a matrícula por ID", responses ={
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Código da matrícula inválida", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Código da matrícula inválida", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public MatriculaDTO buscar(@Parameter(description = "Código do curso", example = "1", required = true) Long matriculaId);

}
