package com.plataforma.plataforma_ead.api.openapi.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Certificado")
public interface CertificadoControllerOpenApi {
	
	@Operation(summary = "Emite certificado", responses= {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_PDF_VALUE, schema = @Schema(type = "string", format = "binary")),
			}),
			@ApiResponse(responseCode = "400", description = "ID da matricula inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Certificado de usuário não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),

})
	public ResponseEntity<Resource> emitirCertificado(@Parameter(description = "ID da matricula", example = "1", required = true) Long matriculaId) throws Exception;

}
