package com.plataforma.plataforma_ead.api.openapi.controller;

import com.plataforma.plataforma_ead.api.dto.input.EsqueciSenhaInput;
import com.plataforma.plataforma_ead.api.dto.input.NovaSenhaInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Redefinir Senha")
public interface RecuperarSenhaControllerOpenApi {

	@Operation(summary = "Solicita alteração de senha esquecida", responses = {
			@ApiResponse(responseCode = "204", description = "Codigo de alteração gerado"),
	})
    public void solicitar(@RequestBody(description = "Representação do email", required = true) EsqueciSenhaInput input);
    
    @Operation(summary = "Altera senha", responses = {
			@ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
	})
    public void alterar(@RequestBody(description = "Representação da nova senha", required = true) NovaSenhaInput input);
}
