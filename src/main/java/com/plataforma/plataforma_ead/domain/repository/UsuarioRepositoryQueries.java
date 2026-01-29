package com.plataforma.plataforma_ead.domain.repository;

import com.plataforma.plataforma_ead.domain.model.FotoUsuario;

public interface UsuarioRepositoryQueries {

	FotoUsuario save(FotoUsuario foto);
	
	void delete(FotoUsuario foto);
	
}