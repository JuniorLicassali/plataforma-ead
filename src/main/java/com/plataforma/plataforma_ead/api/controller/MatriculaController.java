package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.MatriculaDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.MatriculaDTO;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.service.MatriculaService;

@RestController
@RequestMapping(path = "/matriculas", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatriculaController {
	
	@Autowired
	private MatriculaService matriculaService;
	
	@Autowired
	private MatriculaDTOAssembler matriculaDTOAssembler;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@GetMapping
	public List<MatriculaDTO> listar() {
		List<Matricula> matriculas = matriculaRepository.findAll();
		
		return matriculaDTOAssembler.toCollectionDTO(matriculas);
	}

	@GetMapping("/{matriculaId}")
	public MatriculaDTO buscar(@PathVariable Long matriculaId) {
		Matricula matricula = matriculaService.buscarOuFalhar(matriculaId);
		
		return matriculaDTOAssembler.toDTO(matricula);
	}
	
}
