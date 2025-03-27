package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.ModuloDTO;
import com.plataforma.plataforma_ead.domain.model.Modulo;

@Component
public class ModuloDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ModuloDTO toDTO(Modulo modulo) {
		return modelMapper.map(modulo, ModuloDTO.class);
	}
	
	
	public List<ModuloDTO> toCollectionDTO(Collection<Modulo> modulos) {
		return modulos.stream()
				.map(modulo -> toDTO(modulo))
				.collect(Collectors.toList());
	}
	
}
