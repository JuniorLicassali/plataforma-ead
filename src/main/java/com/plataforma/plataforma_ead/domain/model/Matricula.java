package com.plataforma.plataforma_ead.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
	    name = "matricula",
	    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "curso_id"})
	)
public class Matricula {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataMatricula;
	
	@Enumerated(EnumType.STRING)
	private StatusMatricula statusMatricula;
	
	@OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "curso_id")
	private Curso curso;
	
	@OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL)
    private QuestionarioUsuario questionarioUsuario;
	
}
