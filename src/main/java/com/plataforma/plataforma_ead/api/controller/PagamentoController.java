package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.PagamentoDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.PagamentoDTO;
import com.plataforma.plataforma_ead.api.dto.input.PagamentoInput;
import com.plataforma.plataforma_ead.api.openapi.controller.PagamentoControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.core.security.PlataformaSecurity;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.repository.PagamentoRepository;
import com.plataforma.plataforma_ead.domain.service.PagamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PagamentoController implements PagamentoControllerOpenApi {
	
	private final PagamentoRepository pagamentoRepository;
	private final PagamentoService pagamentoService;
	private final PagamentoDTOAssembler pagamentoDTOAssembler;
	private final PlataformaSecurity plataformaSecurity;
	
	@CheckSecurity.Pagamento.PodeListar
	@Override
	@GetMapping
	public List<PagamentoDTO> listar() {
		List<PagamentoDTO> pagamentos = pagamentoDTOAssembler.toCollectionDTO(pagamentoRepository.findAll());
		
		return pagamentos;
	}
	
	@CheckSecurity.Pagamento.PodeConsultar
	@Override
	@GetMapping("/{pagamentoId}")
	public PagamentoDTO buscar(@PathVariable Long pagamentoId) {
		PagamentoDTO pagamento = pagamentoDTOAssembler.toDTO(pagamentoService.buscarOuFalhar(pagamentoId));
		
		return pagamento;
	}
	
	@CheckSecurity.Pagamento.PodeCriar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PagamentoDTO criarPagamento(@RequestBody @Valid PagamentoInput pagamentoInput) {
		Long usuarioId = plataformaSecurity.getUsuarioId();
		Pagamento pagamento = pagamentoService.criarPagamento(usuarioId, pagamentoInput.getMatricula().getCursoId(), pagamentoInput.getMetodoPagamento());
		
		return pagamentoDTOAssembler.toDTO(pagamento);
	}
	

}
