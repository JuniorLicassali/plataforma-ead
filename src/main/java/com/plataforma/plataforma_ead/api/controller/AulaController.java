package com.plataforma.plataforma_ead.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.plataforma.plataforma_ead.api.assembler.AulaDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.AulaInputDisassembler;
import com.plataforma.plataforma_ead.api.dto.AulaDTO;
import com.plataforma.plataforma_ead.api.dto.input.AulaInput;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.service.CadastroAulaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos/{cursoId}/modulos/{moduloId}/aulas")
public class AulaController {

	@Autowired
	private CadastroAulaService aulaService;
	
	@Autowired
	private AulaDTOAssembler aulaAssembler;
	
	@Autowired
	private AulaInputDisassembler aulaDisassembler;
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public AulaDTO adicionar(@PathVariable Long cursoId, @PathVariable Long moduloId, @RequestPart("aulaInput") @Valid AulaInput aulaInput, @RequestPart("video") MultipartFile video) throws Exception {
		Aula aula = aulaDisassembler.toDomainObject(aulaInput);
		
		return aulaAssembler.toDTO(aulaService.salvar(cursoId, aula.getModulo().getId(), aula, video));
	}
	
	@DeleteMapping("/{aulaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void  excluir(@PathVariable Long cursoId, @PathVariable Long moduloId, @PathVariable Long aulaId) {
		aulaService.excluir(aulaId);
	}
	
}
