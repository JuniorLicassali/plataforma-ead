package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.FotoUsuario;
import com.plataforma.plataforma_ead.domain.model.Usuario;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQueries  {
	
	Optional<Usuario> findByEmail(String email);
	
	@Query("select f from FotoUsuario f join f.usuario u where u.id = :usuarioId")
	Optional<FotoUsuario> findFotoById(Long usuarioId);
	
}