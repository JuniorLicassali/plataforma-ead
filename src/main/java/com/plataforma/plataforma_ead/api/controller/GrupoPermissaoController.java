package com.plataforma.plataforma_ead.api.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.PermissaoDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.PermissaoDTO;
import com.plataforma.plataforma_ead.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.domain.model.Grupo;
import com.plataforma.plataforma_ead.domain.model.Permissao;
import com.plataforma.plataforma_ead.domain.service.CadastroGrupoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	private final CadastroGrupoService cadastroGrupo;
	
	private final PermissaoDTOAssembler permissaoDTOAssembler;
	
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		Collection<Permissao> permissoesDTO = grupo.getPermissoes();
		
		
		return permissaoDTOAssembler.toCollectionDTO(permissoesDTO);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
		
	}

}