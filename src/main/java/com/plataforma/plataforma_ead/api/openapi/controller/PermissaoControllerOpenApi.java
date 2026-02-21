package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import com.plataforma.plataforma_ead.api.dto.PermissaoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Permissões")
public interface PermissaoControllerOpenApi {

	@Operation(summary = "Lista as permissões")
	List<PermissaoDTO> listar();

}