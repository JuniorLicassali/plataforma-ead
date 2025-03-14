package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
	
	Optional<Usuario> findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u JOIN u.matriculas m WHERE m.id = :matriculaId")
	Optional<Usuario> findByMatriculaId(Long matriculaId);
	
	@Query("SELECT m FROM Usuario u JOIN u.matriculas m")
    List<Matricula> findAllMatriculas();
	
}