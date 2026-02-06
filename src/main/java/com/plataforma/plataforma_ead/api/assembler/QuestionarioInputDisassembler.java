package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.QuestionarioInput;
import com.plataforma.plataforma_ead.domain.model.Questionario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionarioInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Questionario toDomainObject(QuestionarioInput questionarioInput) {
		return modelMapper.map(questionarioInput, Questionario.class);
	}
	
	public void copyToDomainObject(QuestionarioInput questionarioInput, Questionario questionario) {
		modelMapper.map(questionarioInput, questionario);
	}
	
}
