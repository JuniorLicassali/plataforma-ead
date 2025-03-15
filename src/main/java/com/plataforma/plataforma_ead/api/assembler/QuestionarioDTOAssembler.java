package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.QuestionarioDTO;
import com.plataforma.plataforma_ead.domain.model.Questionario;

@Component
public class QuestionarioDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public QuestionarioDTO toDTO(Questionario questionario) {
		return modelMapper.map(questionario, QuestionarioDTO.class);
	}
	
	public List<QuestionarioDTO> toCollectionDTO(Collection<Questionario> questionarios) {
		return questionarios.stream()
				.map(questionario -> toDTO(questionario))
				.collect(Collectors.toList());
	}

}
