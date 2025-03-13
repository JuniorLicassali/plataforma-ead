package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.MatriculaDTO;
import com.plataforma.plataforma_ead.domain.model.Matricula;

@Component
public class MatriculaDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MatriculaDTO toDTO(Matricula matricula) {
		return modelMapper.map(matricula, MatriculaDTO.class);
	}
	
	
	public List<MatriculaDTO> toCollectionDTO(Collection<Matricula> matriculas) {
		return matriculas.stream()
				.map(matricula -> toDTO(matricula))
				.collect(Collectors.toList());
	}
	
}
