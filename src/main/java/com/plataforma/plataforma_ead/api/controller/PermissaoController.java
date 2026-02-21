package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.PermissaoDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.PermissaoDTO;
import com.plataforma.plataforma_ead.api.openapi.controller.PermissaoControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.domain.model.Permissao;
import com.plataforma.plataforma_ead.domain.repository.PermissaoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PermissaoController implements PermissaoControllerOpenApi {

	private final PermissaoRepository permissaoRepository;
	
	private final PermissaoDTOAssembler permissaoDTOAssembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public List<PermissaoDTO> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
		
		return permissaoDTOAssembler.toCollectionDTO(todasPermissoes);
	}
	
}