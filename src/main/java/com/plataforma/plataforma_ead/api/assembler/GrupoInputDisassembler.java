package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.GrupoInput;
import com.plataforma.plataforma_ead.domain.model.Grupo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GrupoInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
	
}