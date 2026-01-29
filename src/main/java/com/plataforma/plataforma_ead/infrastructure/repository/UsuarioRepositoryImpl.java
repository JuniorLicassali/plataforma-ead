package com.plataforma.plataforma_ead.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.FotoUsuario;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoUsuario save(FotoUsuario foto) {
		return manager.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoUsuario foto) {
		manager.remove(foto);
	}

}
