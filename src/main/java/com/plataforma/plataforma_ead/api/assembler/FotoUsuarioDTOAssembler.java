package com.plataforma.plataforma_ead.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.FotoUsuarioDTO;
import com.plataforma.plataforma_ead.domain.model.FotoUsuario;

@Component
public class FotoUsuarioDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FotoUsuarioDTO toModel(FotoUsuario foto) {
		FotoUsuarioDTO fotoUsuarioDTO = modelMapper.map(foto, FotoUsuarioDTO.class);

		return fotoUsuarioDTO;
	}

}