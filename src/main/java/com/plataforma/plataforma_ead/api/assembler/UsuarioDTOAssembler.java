package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.UsuarioDTO;
import com.plataforma.plataforma_ead.domain.model.Usuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioDTOAssembler {
	
	private final ModelMapper modelMapper;

	public UsuarioDTO toDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toDTO(usuario))
				.collect(Collectors.toList());
	}
	
}
