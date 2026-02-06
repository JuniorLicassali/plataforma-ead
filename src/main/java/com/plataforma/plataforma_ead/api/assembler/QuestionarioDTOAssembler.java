package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.QuestionarioDTO;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionarioDTOAssembler {
	
	private final ModelMapper modelMapper;
	
	public QuestionarioDTO toDTO(Questionario questionario) {
		return modelMapper.map(questionario, QuestionarioDTO.class);
	}
	
	public QuestionarioDTO toDTO(QuestionarioUsuario questionario) {
		return modelMapper.map(questionario, QuestionarioDTO.class);
	}
	
	public List<QuestionarioDTO> toCollectionDTO(Collection<Questionario> questionarios) {
		return questionarios.stream()
				.map(questionario -> toDTO(questionario))
				.collect(Collectors.toList());
	}

}
