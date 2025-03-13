package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.UsuarioDTO;
import com.plataforma.plataforma_ead.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public UsuarioDTO toDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toDTO(usuario))
				.collect(Collectors.toList());
	}
	
}
