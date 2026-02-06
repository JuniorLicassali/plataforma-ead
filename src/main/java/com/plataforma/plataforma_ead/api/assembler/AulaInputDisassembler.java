package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.AulaInput;
import com.plataforma.plataforma_ead.domain.model.Aula;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AulaInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Aula toDomainObject(AulaInput aulaInput) {
		return modelMapper.map(aulaInput, Aula.class);
	}
	
	public void copyToDomainObject(AulaInput aulaInput, Aula aula) {
        modelMapper.map(aulaInput, aula);
	}
	
}
