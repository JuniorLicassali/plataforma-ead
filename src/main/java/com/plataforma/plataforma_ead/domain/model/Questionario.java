package com.plataforma.plataforma_ead.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Questionario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String descricao;
	private Boolean ativo = Boolean.TRUE;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFechamento;
	
	@OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL)
	private List<Pergunta> perguntas = new ArrayList<>();
	
	@OneToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
	
	@OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL)
    private List<QuestionarioUsuario> questionariosUsuarios;
	
}
