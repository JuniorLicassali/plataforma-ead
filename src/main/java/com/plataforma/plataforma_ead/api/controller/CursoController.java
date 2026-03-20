package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.plataforma.plataforma_ead.api.assembler.CursoDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.CursoInputDisassembler;
import com.plataforma.plataforma_ead.api.assembler.CursoResumoDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.CursoDTO;
import com.plataforma.plataforma_ead.api.dto.CursoResumoDTO;
import com.plataforma.plataforma_ead.api.dto.input.CursoInput;
import com.plataforma.plataforma_ead.api.openapi.controller.CursoControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.core.security.PlataformaSecurity;
import com.plataforma.plataforma_ead.domain.filter.CursoFilter;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.service.CadastroCursoService;
import com.plataforma.plataforma_ead.infrastructure.repository.spec.CursoSpecs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/cursos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CursoController implements CursoControllerOpenApi {
	
	private final CadastroCursoService cursoService;
	private final CursoRepository cursoRepository;
	private final CursoDTOAssembler cursoDTOAssembler;
	private final CursoResumoDTOAssembler cursoResumoDTOAssembler;
	private final CursoInputDisassembler cursoInputDisassembler;
	private final PlataformaSecurity plataformaSecurity;
	
	@Override
	@GetMapping
	public Page<CursoResumoDTO> listar(CursoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		Page<Curso> cursosPage = cursoRepository.findAll(
	            CursoSpecs.usandoFiltro(filtro), pageable);
		
		return cursosPage.map(curso -> cursoResumoDTOAssembler.toDTO(curso));
	}
	
	@Override
	@GetMapping("/matriculados")
	public List<CursoDTO> listarMeusCursos() {
		List<Curso> cursos = cursoRepository.findCursosByUsuarioId(plataformaSecurity.getUsuarioId());
		
		return cursoDTOAssembler.toCollectionDTO(cursos);
	}
	
	@CheckSecurity.Curso.PodeConsultar
	@Override
	@GetMapping("/{cursoId}")
	public CursoDTO buscar(@PathVariable Long cursoId) {
		CursoDTO curso = cursoDTOAssembler.toDTO(cursoService.buscarOuFalhar(cursoId));
		
		return curso;
	}
	
	@CheckSecurity.Curso.PodeCriar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CursoDTO adicionar(@RequestBody @Valid CursoInput cursoInput) {
		Curso curso = cursoInputDisassembler.toDomainObject(cursoInput);
		cursoService.salvar(curso);
		
		return cursoDTOAssembler.toDTO(curso);
	}
	
	@CheckSecurity.Curso.PodeEditar
	@Override
	@PutMapping("/{cursoId}")
	@ResponseStatus(HttpStatus.OK)
	public CursoDTO atualizar(@PathVariable Long cursoId, @RequestBody @Valid CursoInput cursoInput) {
		Curso cursoAtual = cursoService.buscarOuFalhar(cursoId);
		cursoInputDisassembler.copyToDomainObject(cursoInput, cursoAtual);
		
		cursoAtual = cursoService.salvar(cursoAtual);
		
		return cursoDTOAssembler.toDTO(cursoAtual);
	}
	
	@CheckSecurity.Curso.PodeEditar
	@Override
	@PutMapping("/{cursoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long cursoId) {
		cursoService.ativar(cursoId);
	}
	
	@CheckSecurity.Curso.PodeEditar
	@Override
	@DeleteMapping("/{cursoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long cursoId) {
		cursoService.inativar(cursoId);
	}

}
