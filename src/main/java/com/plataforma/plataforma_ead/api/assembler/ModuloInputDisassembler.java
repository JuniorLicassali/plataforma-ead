package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.ModuloInput;
import com.plataforma.plataforma_ead.domain.model.Modulo;

@Component
public class ModuloInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Modulo toDomainObject(ModuloInput moduloInput) {
		return modelMapper.map(moduloInput, Modulo.class);
	}
	
	public void copyToDomainObject(ModuloInput moduloInput, Modulo modulo) {
		modelMapper.map(moduloInput, modulo);
	}
	
}
