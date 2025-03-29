package com.plataforma.plataforma_ead.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	@Query("SELECT m FROM Matricula m WHERE m.usuario.id = :usuarioId AND m.curso.id = :cursoId")
	Optional<Matricula> findByUsuarioIdAndCursoId(@Param("usuarioId") Long usuarioId, @Param("cursoId") Long cursoId);
	
	@Query("SELECT m FROM Matricula m WHERE m.usuario.id = :usuarioId AND m.curso.id = :cursoId AND m.statusMatricula = :status")
	Optional<Matricula> findByUsuarioIdAndCursoIdAndStatus(@Param("usuarioId") Long usuarioId, @Param("cursoId") Long cursoId, @Param("status") StatusMatricula status);
	
	@Query("SELECT m FROM Matricula m WHERE m.usuario.id = :usuarioId")
	List<Matricula> findMatriculasByUsuarioId(@Param("usuarioId") Long usuarioId);
	
}
