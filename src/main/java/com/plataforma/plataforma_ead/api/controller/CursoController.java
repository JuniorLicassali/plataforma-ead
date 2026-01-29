package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.CursoDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.CursoInputDisassembler;
import com.plataforma.plataforma_ead.api.dto.CursoDTO;
import com.plataforma.plataforma_ead.api.dto.input.CursoInput;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.service.CadastroCursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/cursos", produces = MediaType.APPLICATION_JSON_VALUE)
public class CursoController {
	
	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CursoDTOAssembler cursoDTOAssembler;
	
	@Autowired
	private CursoInputDisassembler cursoInputDisassembler;
	
	@GetMapping
	public List<CursoDTO> listar() {
		List<CursoDTO> cursos = cursoDTOAssembler.toCollectionDTO(cursoRepository.findAll());
		
		return cursos;
	}
	
	@GetMapping("/{cursoId}")
	public CursoDTO buscar(@PathVariable Long cursoId) {
		CursoDTO curso = cursoDTOAssembler.toDTO(cursoService.buscarOuFalhar(cursoId));
		
		return curso;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CursoDTO adicionar(@RequestBody @Valid CursoInput cursoInput) {
		Curso curso = cursoInputDisassembler.toDomainObject(cursoInput);
		cursoService.salvar(curso);
		
		return cursoDTOAssembler.toDTO(curso);
	}
	
	@PutMapping("/{cursoId}")
	public CursoDTO atualizar(@PathVariable Long cursoId, @RequestBody @Valid CursoInput cursoInput) {
		Curso cursoAtual = cursoService.buscarOuFalhar(cursoId);
		cursoInputDisassembler.copyToDomainObject(cursoInput, cursoAtual);
		
		cursoAtual = cursoService.salvar(cursoAtual);
		
		return cursoDTOAssembler.toDTO(cursoAtual);
	}
	
	@PutMapping("/{cursoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long cursoId) {
		cursoService.ativar(cursoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{cursoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long cursoId) {
		cursoService.inativar(cursoId);
		return ResponseEntity.noContent().build();
	}

}
