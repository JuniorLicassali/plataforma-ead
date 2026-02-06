package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.MatriculaDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.MatriculaDTO;
import com.plataforma.plataforma_ead.api.openapi.controller.MatriculaControllerOpenApi;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.service.MatriculaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/matriculas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MatriculaController implements MatriculaControllerOpenApi {
	
	private final MatriculaService matriculaService;
	private final MatriculaDTOAssembler matriculaDTOAssembler;
	private final MatriculaRepository matriculaRepository;
	
	@Override
	@GetMapping
	public List<MatriculaDTO> listar() {
		List<Matricula> matriculas = matriculaRepository.findAll();
		
		return matriculaDTOAssembler.toCollectionDTO(matriculas);
	}

	@Override
	@GetMapping("/{matriculaId}")
	public MatriculaDTO buscar(@PathVariable Long matriculaId) {
		Matricula matricula = matriculaService.buscarOuFalhar(matriculaId);
		
		return matriculaDTOAssembler.toDTO(matricula);
	}
	
}
