package com.plataforma.plataforma_ead.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoUsuario {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "usuario_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Usuario usuario;
	
	private String nomeArquivo;
	private String contentType;
	private Long tamanho;
	
	public Long getUsuarioId() {
		if (getUsuario() != null) {
			return getUsuario().getId();
		}
		
		return null;
	}

}
