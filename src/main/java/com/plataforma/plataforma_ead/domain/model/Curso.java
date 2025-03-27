package com.plataforma.plataforma_ead.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Curso {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
	private Set<Matricula> matriculas = new HashSet<>();
	
	@OneToOne(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Questionario questionario;
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Modulo> modulos = new ArrayList<>();
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
}
