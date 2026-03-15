package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.CursoResumoDTO;
import com.plataforma.plataforma_ead.domain.model.Curso;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CursoResumoDTOAssembler {

	private final ModelMapper modelMapper;
	
	public CursoResumoDTO toDTO(Curso curso) {
		return modelMapper.map(curso, CursoResumoDTO.class);
	}
	
	
	public List<CursoResumoDTO> toCollectionDTO(Collection<Curso> cursos) {
		return cursos.stream()
				.map(curso -> toDTO(curso))
				.collect(Collectors.toList());
	}
	
}
