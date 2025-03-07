package com.plataforma.plataforma_ead.domain.model;

import java.util.HashSet;
import java.util.Set;

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
public class Grupo {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private Set<Permissao> permissoes = new HashSet<>();
	
	public boolean adicionarPermissao(Permissao permissao) {
		return permissoes.add(permissao);
	}
	
	public boolean removerPermissao(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
	
}
