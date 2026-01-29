package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.service.CadastroModuloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/cursos/{cursoId}/modulos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModuloController {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CadastroModuloService moduloService;
	
	@Autowired
	private ModuloDTOAssembler moduloAssembler;
	
	@Autowired
	private ModuloInputDisassembler inputDisassembler;
	
	@GetMapping
	public List<ModuloDTO> listar(@PathVariable Long cursoId) {
		return moduloAssembler.toCollectionDTO(cursoRepository.findAllModulosByCursoId(cursoId));
	}
	
	@GetMapping("/{moduloId}")
	public ModuloDTO buscar(@PathVariable Long cursoId, @PathVariable Long moduloId) {
		return moduloAssembler.toDTO(moduloService.buscarOuFalhar(moduloId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ModuloDTO adicionar(@PathVariable Long cursoId, @RequestBody @Valid ModuloInput moduloInput) {
		Modulo modulo = inputDisassembler.toDomainObject(moduloInput);
		modulo = moduloService.salvar(modulo, cursoId);
		
		return moduloAssembler.toDTO(modulo);
	}
	
	@PutMapping("/{moduloId}")
	public ModuloDTO atualizar(@PathVariable Long moduloId, @RequestBody @Valid ModuloInput moduloInput) {
		
		Modulo moduloAtual = moduloService.buscarOuFalhar(moduloId);
		inputDisassembler.copyToDomainObject(moduloInput, moduloAtual);
		moduloAtual = moduloService.salvar(moduloAtual, moduloAtual.getCurso().getId());
		
		return moduloAssembler.toDTO(moduloAtual);
	}
	
	@DeleteMapping("/{moduloId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long moduloId) {
		moduloService.excluir(moduloId);
	}
	
}
