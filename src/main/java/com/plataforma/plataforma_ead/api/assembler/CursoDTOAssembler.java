package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.CursoDTO;
import com.plataforma.plataforma_ead.domain.model.Curso;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CursoDTOAssembler {

	private final ModelMapper modelMapper;
	
	public CursoDTO toDTO(Curso curso) {
		return modelMapper.map(curso, CursoDTO.class);
	}
	
	
	public List<CursoDTO> toCollectionDTO(Collection<Curso> cursos) {
		return cursos.stream()
				.map(curso -> toDTO(curso))
				.collect(Collectors.toList());
	}
	
}
