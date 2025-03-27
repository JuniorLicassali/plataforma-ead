package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.AulaInput;
import com.plataforma.plataforma_ead.domain.model.Aula;

@Component
public class AulaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Aula toDomainObject(AulaInput aulaInput) {
		return modelMapper.map(aulaInput, Aula.class);
	}
	
	public void copyToDomainObject(AulaInput aulaInput, Aula aula) {
        modelMapper.map(aulaInput, aula);
	}
	
}
