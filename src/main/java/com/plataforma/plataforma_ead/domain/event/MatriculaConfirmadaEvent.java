package com.plataforma.plataforma_ead.domain.event;

import com.plataforma.plataforma_ead.domain.model.Matricula;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatriculaConfirmadaEvent {
	private Matricula matricula;
}
