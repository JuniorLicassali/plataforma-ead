package com.plataforma.plataforma_ead.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pergunta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String enunciado;
	
	@Column(nullable = false)
	private List<String> opcoes = new ArrayList<>();
	
	@Column(nullable = false)
	private String respostaCorreta;
	
	@ManyToOne
    @JoinColumn(name = "questionario_id", nullable = false)
    private Questionario questionario;
    
    public void embaralharOpcoes() {
        Collections.shuffle(opcoes);
    }

    public boolean verificarResposta(String resposta) {
        return respostaCorreta.equals(resposta);
    }
}
