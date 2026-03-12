package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.input.PerguntaInput;
import com.plataforma.plataforma_ead.domain.model.Pergunta;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerguntaInputDisassembler {
	
	private final ModelMapper modelMapper;

	public Pergunta toDomainObject(PerguntaInput perguntaInput) {
        return modelMapper.map(perguntaInput, Pergunta.class);
    }
    
    public void copyToDomainObject(PerguntaInput perguntaInput, Pergunta pergunta) {
        modelMapper.map(perguntaInput, pergunta);
    }
}