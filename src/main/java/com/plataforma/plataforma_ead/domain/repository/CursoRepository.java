package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
