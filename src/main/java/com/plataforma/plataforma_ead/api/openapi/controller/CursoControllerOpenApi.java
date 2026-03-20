package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.plataforma.plataforma_ead.api.dto.CursoDTO;
import com.plataforma.plataforma_ead.api.dto.CursoResumoDTO;
import com.plataforma.plataforma_ead.api.dto.input.CursoInput;
import com.plataforma.plataforma_ead.domain.filter.CursoFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cursos")
public interface CursoControllerOpenApi {

	@Operation(
			summary = "Lista todos os cursos",
			parameters = {
					@Parameter(in = ParameterIn.QUERY, name = "nome",
							description = "Nome do curso para filtro da pesquisa",
							example = "Administração", schema = @Schema(type = "string")),
					@Parameter(in = ParameterIn.QUERY, name = "precoMin",
							description = "Preço minimo para filtro da pesquisa",
							example = "500", schema = @Schema(type = "integer")),
					@Parameter(in = ParameterIn.QUERY, name = "precoMax",
							description = "Preço máximo para filtro da pesquisa",
							example = "600", schema = @Schema(type = "integer")),
					@Parameter(in = ParameterIn.QUERY, name = "ativo",
							description = "Ativo para filtro da pesquisa",
							example = "true", schema = @Schema(type = "boolean"))
			}
	)
	@PageableAsQueryParam
	public Page<CursoResumoDTO> listar(@Parameter(hidden = true) CursoFilter filtro, @Parameter(hidden = true) Pageable pageable);
	
	@Operation(summary = "Lista todos os cursos matriculados")
	public List<CursoDTO> listarMeusCursos();
	
	@Operation(summary = "Busca o curso por ID", responses ={
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Código curso inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public CursoDTO buscar(@Parameter(description = "Código do curso", example = "1", required = true) Long cursoId);
	
	@Operation(summary = "Cadastra um curso", responses ={
			@ApiResponse(responseCode = "201", description = "Curso cadastrado")
	})
	public CursoDTO adicionar(@RequestBody(description = "Representação de um novo curso", required = true) CursoInput cursoInput);
	
	@Operation(summary = "Atualiza um curso por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Curso atualizado"),
			@ApiResponse(responseCode = "404", description = "Curso não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public CursoDTO atualizar(@Parameter(description = "Código do curso", example = "1", required = true) Long cursoId, @RequestBody(description = "Representação de um curso com os novos dados", required = true) CursoInput cursoInput);
	
	@Operation(summary = "Ativa um curso por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Curso ativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Curso não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public void ativar(@Parameter(description = "Código do curso", example = "1", required = true) Long cursoId);
	
	@Operation(summary = "Inativa um curso por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Curso inativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Curso não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public void inativar(@Parameter(description = "Código do curso", example = "1", required = true) Long cursoId);
	
}
