package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.CursoInput;
import com.plataforma.plataforma_ead.domain.model.Curso;

@Component
public class CursoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Curso toDomainObject(CursoInput cursoInput) {
		return modelMapper.map(cursoInput, Curso.class);
	}
	
	public void copyToDomainObject(CursoInput cursoInput, Curso curso) {
		modelMapper.map(cursoInput, curso);
	}
	
}
