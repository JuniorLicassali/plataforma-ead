package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.CursoInput;
import com.plataforma.plataforma_ead.domain.model.Curso;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IdUsuarioTesteQuestionarioInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Curso toDomainObject(CursoInput cursoInput) {
		return modelMapper.map(cursoInput, Curso.class);
	}
	
	public void copyToDomainObject(CursoInput cursoInput, Curso curso) {
		modelMapper.map(cursoInput, curso);
	}
	
}
