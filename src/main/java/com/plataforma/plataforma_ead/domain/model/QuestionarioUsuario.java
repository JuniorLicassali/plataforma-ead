package com.plataforma.plataforma_ead.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "questionario_usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"matricula_id", "questionario_id"}))
public class QuestionarioUsuario {

	@EqualsAndHashCode.Include
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "questionario_id", nullable = false)
    private Questionario questionario;

    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFechamento;
    private Boolean finalizado = false;
    private Double nota;
    
    public QuestionarioUsuario() {
    }
    
    public void finalizar() {
        this.finalizado = true;
    }
	
}
