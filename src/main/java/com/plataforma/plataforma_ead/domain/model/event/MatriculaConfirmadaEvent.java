package com.plataforma.plataforma_ead.domain.model.event;

import com.plataforma.plataforma_ead.domain.model.Matricula;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatriculaConfirmadaEvent {
	private Matricula matricula;
}
