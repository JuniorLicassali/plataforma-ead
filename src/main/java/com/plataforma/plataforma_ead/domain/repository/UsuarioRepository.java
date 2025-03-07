package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Usuario;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
	
	Optional<Usuario> findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u JOIN u.matriculas m WHERE m.id = :matriculaId")
	Optional<Usuario> findByMatriculaId(Long matriculaId);
	
	@Query("SELECT u FROM Usuario u JOIN u.matriculas m WHERE m.pagamentoId = :pagamentoId")
    Optional<Usuario> findByMatriculaPagamentoId(@Param("pagamentoId") Long pagamentoId);
	
}