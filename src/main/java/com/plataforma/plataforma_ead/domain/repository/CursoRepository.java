package com.plataforma.plataforma_ead.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.model.Questionario;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

	@Query("select q from Curso c join c.questionario q where c.id = :cursoId")
	Optional<Questionario> findQuestionarioById(@Param("cursoId") Long cursoId);
	
	@Query("select c from Curso c join FETCH c.questionario where c.id = :cursoId")
	Optional<Curso> findCursoComQuestionario(@Param("cursoId") Long cursoId);
	
	@Query("select m from Curso c join c.modulos m where m.id = :moduloId")
	Optional<Modulo> findModuloById(@Param("moduloId") Long moduloId);
	
	@Query("select a from Curso c join c.modulos.aulas a where a.id = :aulaId")
	Optional<Aula> findAulaById(@Param("aulaId") Long aulaId);
	
	@Query("select m from Curso c join c.modulos m where c.id = :cursoId")
    List<Modulo> findAllModulosByCursoId(@Param("cursoId") Long cursoId);
	
	@Query("select c from Curso c join Matricula m on m.curso.id = c.id where m.usuario.id = :usuarioId")
	List<Curso> findCursosByUsuarioId(@Param("usuarioId") Long usuarioId);
	
	@Modifying
    @Query("delete from Aula a where a.modulo.id = :moduloId")
    void deleteAulasByModuloId(@Param("moduloId") Long moduloId);
	
	@Modifying
	@Query("delete from Aula a where a.id = :aulaId")
	void deleteAulaById(@Param("aulaId") Long aulaId);
	
	@Modifying
    @Query("delete from Modulo m where m.id = :moduloId")
    void deleteModuloById(@Param("moduloId") Long moduloId);
	
	boolean podeEditarCurso(Long usuarioId, Long cursoId);
	
	
}
