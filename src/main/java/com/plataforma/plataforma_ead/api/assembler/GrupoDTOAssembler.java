package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.GrupoDTO;
import com.plataforma.plataforma_ead.domain.model.Grupo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GrupoDTOAssembler {

	private final ModelMapper modelMapper;

	public GrupoDTO toDTO(Grupo grupo) {
		
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toCollectionDTO(Collection<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toDTO(grupo))
				.collect(Collectors.toList());
	}
	
}