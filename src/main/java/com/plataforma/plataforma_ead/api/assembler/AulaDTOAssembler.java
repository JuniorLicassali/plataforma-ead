package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.AulaDTO;
import com.plataforma.plataforma_ead.domain.model.Aula;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AulaDTOAssembler {

	private final ModelMapper modelMapper;
	
	public AulaDTO toDTO(Aula aula) {
		return modelMapper.map(aula, AulaDTO.class);
	}
	
	public List<AulaDTO> toCollectionDTO(Collection<Aula> aulas) {
		return aulas.stream()
				.map(aula -> toDTO(aula))
				.collect(Collectors.toList());
	}
	
}
