package com.plataforma.plataforma_ead.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.model.Questionario;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Query("SELECT q FROM Curso c JOIN c.questionario q WHERE c.id = :cursoId")
	Optional<Questionario> findQuestionarioById(@Param("cursoId") Long cursoId);
	
	@Query("SELECT c FROM Curso c JOIN FETCH c.questionario WHERE c.id = :cursoId")
	Optional<Curso> findCursoComQuestionario(@Param("cursoId") Long cursoId);
	
	@Query("SELECT m FROM Curso c JOIN c.modulos m WHERE m.id = :moduloId")
	Optional<Modulo> findModuloById(@Param("moduloId") Long moduloId);
	
	@Query("SELECT a FROM Curso c JOIN c.modulos.aulas a WHERE a.id = :aulaId")
	Optional<Aula> findAulaById(@Param("aulaId") Long aulaId);
	
	@Query("SELECT m FROM Curso c JOIN c.modulos m WHERE c.id = :cursoId")
    List<Modulo> findAllModulosByCursoId(@Param("cursoId") Long cursoId);
	
	@Modifying
    @Query("DELETE FROM Aula a WHERE a.modulo.id = :moduloId")
    void deleteAulasByModuloId(@Param("moduloId") Long moduloId);
	
	@Modifying
	@Query("DELETE FROM Aula a WHERE a.id = :aulaId")
	void deleteAulaById(@Param("aulaId") Long aulaId);
	
	@Modifying
    @Query("DELETE FROM Modulo m WHERE m.id = :moduloId")
    void deleteModuloById(@Param("moduloId") Long moduloId);
	
}
