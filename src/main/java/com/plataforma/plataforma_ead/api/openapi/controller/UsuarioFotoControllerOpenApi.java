package com.plataforma.plataforma_ead.api.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.plataforma.plataforma_ead.api.dto.FotoUsuarioDTO;
import com.plataforma.plataforma_ead.api.dto.input.FotoUsuarioInput;

@Tag(name = "Usuários")
public interface UsuarioFotoControllerOpenApi {
	
	@Operation(summary = "Atualiza a foto do usuário")
	public FotoUsuarioDTO atualizarFoto(@Parameter(description = "Id do usuário", example = "1", required = true) Long usuarioId,
								   @RequestBody(required = true) FotoUsuarioInput fotoUsuarioInput) throws IOException;
	
	@Operation(summary = "Exclui a foto do usuário", responses = {
			@ApiResponse(responseCode = "204", description = "Foto do usuário excluída"),
			@ApiResponse(responseCode = "400", description = "ID do usuario inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Foto do usuário não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public void excluir(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);
	
	@Operation(summary = "Busca a foto do usuário", responses = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FotoUsuarioDTO.class)),
					@Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
					@Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
			}),
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Foto de usuário não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),

	})
	FotoUsuarioDTO buscar(@Parameter(description = "ID do usuario", example = "1", required = true) Long usuarioId);
	

	@Operation(hidden = true)
	ResponseEntity<?> servir(Long usuarioId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

}
