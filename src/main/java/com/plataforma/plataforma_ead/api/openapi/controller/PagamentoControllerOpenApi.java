package com.plataforma.plataforma_ead.api.openapi.controller;

import java.util.List;

import com.plataforma.plataforma_ead.api.dto.PagamentoDTO;
import com.plataforma.plataforma_ead.api.dto.input.PagamentoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pagamentos")
public interface PagamentoControllerOpenApi {

	@Operation(summary = "Lista pagamentos")
	public List<PagamentoDTO> listar();
	
	@Operation(summary = "Busca pagamento por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do pagamento inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	public PagamentoDTO buscar(@Parameter(description = "ID de um pagamento", example = "1", required = true) Long pagamentoId);
	
	
	@Operation(summary = "Cria um novo pagamento", responses = {
			@ApiResponse(responseCode = "201", description = "Pagamento criado"),
	})
	public PagamentoDTO criarPagamento(@RequestBody(description = "Representação de um novo pagamento", required = true) PagamentoInput pagamentoInput);
	
}
