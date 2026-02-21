package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.PermissaoDTO;
import com.plataforma.plataforma_ead.domain.model.Permissao;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PermissaoDTOAssembler {

	private final ModelMapper modelMapper;
	
	public PermissaoDTO toDTO(Permissao permissao) {
		PermissaoDTO permissaoModel = modelMapper.map(permissao, PermissaoDTO.class);
		return permissaoModel;
	}
	
	public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toDTO(permissao))
				.collect(Collectors.toList());
	}
	
}