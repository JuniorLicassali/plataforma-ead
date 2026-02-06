package com.plataforma.plataforma_ead.domain.service;

import org.springframework.stereotype.Service;

import com.plataforma.plataforma_ead.domain.exception.PermissaoNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.model.Permissao;
import com.plataforma.plataforma_ead.domain.repository.PermissaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroPermissaoService {
	
	
	private final PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
}