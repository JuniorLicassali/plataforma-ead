package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import com.plataforma.plataforma_ead.api.dto.QuestionarioDTO;
import com.plataforma.plataforma_ead.api.dto.QuestionarioUsuarioDTO;
import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.IdUsuarioAbrirQestionarioTesteInput;
import com.plataforma.plataforma_ead.api.dto.input.PerguntaInput;
import com.plataforma.plataforma_ead.api.dto.input.QuestionarioInput;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Questionario")
public interface QuestionarioControllerOpenApi {
	
	@Operation(summary = "Busca questionario por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID de questionario inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Questionario não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public QuestionarioDTO buscar(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @Parameter(description = "ID de um questionario", example = "1", required = true) Long questionarioId);
	
	@Operation(summary = "Iniciar questionario", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Questionario não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Corpo do usuário", required = true))
	public QuestionarioUsuarioDTO iniciarQuestionario(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @jakarta.validation.Valid IdUsuarioAbrirQestionarioTesteInput usuarioId);
	
	@Operation(
		    summary = "Cadastra um questionario", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
		        description = "Representação de um novo questionario", 
		        required = true
		    )
		)
	public QuestionarioDTO criarQuestionario(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @jakarta.validation.Valid QuestionarioInput questionarioInput);
	
	@Operation(summary = "Adiciona perguntas a um questionario", responses = {
			@ApiResponse(responseCode = "201", description = "Perguntas adicionadas"),
	}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Representação de novas perguntas", required = true))
	public QuestionarioDTO adicionarPergunta(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @Parameter(description = "ID de um questionario", example = "1", required = true) Long questionarioId, @jakarta.validation.Valid PerguntaInput perguntaInput);
	
	@Operation(summary = "Enviar respostas de um questionario iniciado", responses = {
			@ApiResponse(responseCode = "201", description = "Respostas enviadas"),
	}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Representação das respostas do questionario", required = true))
	public List<RespostaDTO> enviarRespostas(@Parameter(description = "ID de um curso", example = "1", required = true) Long cursoId, @Parameter(description = "ID de um questionario", example = "1", required = true) Long questionarioId, Long usuarioId, @jakarta.validation.Valid List<RespostaInput> respostasInput) throws Exception;

}
