package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import com.plataforma.plataforma_ead.api.dto.ModuloDTO;
import com.plataforma.plataforma_ead.api.dto.input.ModuloInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Módulos")
public interface ModuloControllerOpenApi {
	
	@Operation(summary = "Lista módulos de um curso a partir do ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do curso inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Curso não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public List<ModuloDTO> listar(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId);
	
	@Operation(summary = "Busca módulo por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do módulo inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Módulo não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public ModuloDTO buscar(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @Parameter(description = "ID de um módulo", example = "1", required = true) Long moduloId);

	@Operation(summary = "Cadastra um módulo", responses = {
			@ApiResponse(responseCode = "201", description = "Módulo cadastrado"),
	})
	public ModuloDTO adicionar(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @RequestBody(description = "Representação de um novo módulo", required = true) ModuloInput moduloInput);
	
	@Operation(summary = "Atualiza um módulo por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Módulo atualizado"),
			@ApiResponse(responseCode = "404", description = "Módulo não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public ModuloDTO atualizar(@Parameter(description = "ID de um módulo", example = "1", required = true) Long moduloId, @RequestBody(description = "Representação de um módulo com novos dados", required = true) ModuloInput moduloInput);
	
	@Operation(summary = "Exclui um módulo por ID", responses = {
			@ApiResponse(responseCode = "204")
	})
	public void excluir(@Parameter(description = "ID de um módulo", example = "1", required = true) Long moduloId);
	
}
