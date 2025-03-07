package com.plataforma.plataforma_ead.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	private List<Matricula> matriculas;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "dateTime")
	private OffsetDateTime dataCadastro;
	
	private Set<Grupo> grupos = new HashSet<>();
	
	public boolean senhaCoincideCom(String senha) {
		return getSenha().equals(senha);
	}

	public boolean senhaNaoCoincideCom(String senha) {
		return !senhaCoincideCom(senha);
	}
	
	public boolean adicionarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}
	
	public boolean removerGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
	
}
