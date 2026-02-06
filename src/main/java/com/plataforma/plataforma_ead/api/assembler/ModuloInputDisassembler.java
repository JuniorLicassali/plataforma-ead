package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.ModuloInput;
import com.plataforma.plataforma_ead.domain.model.Modulo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModuloInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Modulo toDomainObject(ModuloInput moduloInput) {
		return modelMapper.map(moduloInput, Modulo.class);
	}
	
	public void copyToDomainObject(ModuloInput moduloInput, Modulo modulo) {
		modelMapper.map(moduloInput, modulo);
	}
	
}
