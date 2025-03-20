package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.QuestionarioUsuarioDTO;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;

@Component
public class QuestionarioUsuarioDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public QuestionarioUsuarioDTO toDTO(QuestionarioUsuario questionarioUsuario) {
		return modelMapper.map(questionarioUsuario, QuestionarioUsuarioDTO.class);
	}
	
	
	public List<QuestionarioUsuarioDTO> toCollectionDTO(Collection<QuestionarioUsuario> questionariosUsuario) {
		return questionariosUsuario.stream()
				.map(questionarioUsuario -> toDTO(questionarioUsuario))
				.collect(Collectors.toList());
	}
	
	
}
