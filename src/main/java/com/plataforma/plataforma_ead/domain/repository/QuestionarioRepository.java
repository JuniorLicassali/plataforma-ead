package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Questionario;

@Repository
public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

}
