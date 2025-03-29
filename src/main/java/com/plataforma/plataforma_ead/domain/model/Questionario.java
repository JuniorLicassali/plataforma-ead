package com.plataforma.plataforma_ead.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL)
    private List<QuestionarioUsuario> questionariosUsuarios;
	
}
