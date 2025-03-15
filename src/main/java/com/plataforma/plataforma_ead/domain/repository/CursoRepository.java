package com.plataforma.plataforma_ead.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Questionario;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Query("SELECT q FROM Curso c JOIN c.questionario q")
	Optional<Questionario> findQuestionarioById(Long cursoId);
	
}
