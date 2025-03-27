package com.plataforma.plataforma_ead.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.AulaDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.AulaInputDisassembler;
import com.plataforma.plataforma_ead.api.dto.AulaDTO;
import com.plataforma.plataforma_ead.api.dto.input.AulaInput;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.service.CadastroAulaService;

@RestController
@RequestMapping("/cursos/{cursoId}/modulos/{moduloId}/aulas")
public class AulaController {

	@Autowired
	private CadastroAulaService aulaService;
	
	@Autowired
	private AulaDTOAssembler aulaAssembler;
	
	@Autowired
	private AulaInputDisassembler aulaDisassembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AulaDTO adicionar(@PathVariable Long cursoId, @PathVariable Long moduloId, @RequestBody AulaInput aulaInput) {
		Aula aula = aulaDisassembler.toDomainObject(aulaInput);
		
		return aulaAssembler.toDTO(aulaService.salvar(aula, cursoId, moduloId));
	}
	
	@DeleteMapping("/{aulaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void  excluir(@PathVariable Long cursoId, @PathVariable Long moduloId, @PathVariable Long aulaId) {
		aulaService.excluir(aulaId);
	}
	
}
