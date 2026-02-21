package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.ModuloDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.ModuloInputDisassembler;
import com.plataforma.plataforma_ead.api.dto.ModuloDTO;
import com.plataforma.plataforma_ead.api.dto.input.ModuloInput;
import com.plataforma.plataforma_ead.api.openapi.controller.ModuloControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.service.CadastroModuloService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/cursos/{cursoId}/modulos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ModuloController implements ModuloControllerOpenApi {
	
	private final CursoRepository cursoRepository;
	private final CadastroModuloService moduloService;
	private final ModuloDTOAssembler moduloAssembler;
	private final ModuloInputDisassembler inputDisassembler;
	
	@CheckSecurity.Modulo.PodeConsultar
	@Override
	@GetMapping
	public List<ModuloDTO> listar(@PathVariable Long cursoId) {
		return moduloAssembler.toCollectionDTO(cursoRepository.findAllModulosByCursoId(cursoId));
	}
	
	@CheckSecurity.Modulo.PodeEditar
	@Override
	@GetMapping("/{moduloId}")
	public ModuloDTO buscar(@PathVariable Long cursoId, @PathVariable Long moduloId) {
		return moduloAssembler.toDTO(moduloService.buscarOuFalhar(moduloId));
	}

	@CheckSecurity.Modulo.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ModuloDTO adicionar(@PathVariable Long cursoId, @RequestBody @Valid ModuloInput moduloInput) {
		Modulo modulo = inputDisassembler.toDomainObject(moduloInput);
		modulo = moduloService.salvar(modulo, cursoId);
		
		return moduloAssembler.toDTO(modulo);
	}
	
	@CheckSecurity.Modulo.PodeEditar
	@Override
	@PutMapping("/{moduloId}")
	public ModuloDTO atualizar(@PathVariable Long moduloId, @RequestBody @Valid ModuloInput moduloInput) {
		
		Modulo moduloAtual = moduloService.buscarOuFalhar(moduloId);
		inputDisassembler.copyToDomainObject(moduloInput, moduloAtual);
		moduloAtual = moduloService.salvar(moduloAtual, moduloAtual.getCurso().getId());
		
		return moduloAssembler.toDTO(moduloAtual);
	}
	
	@CheckSecurity.Modulo.PodeEditar
	@Override
	@DeleteMapping("/{moduloId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long moduloId) {
		moduloService.excluir(moduloId);
	}
	
}
