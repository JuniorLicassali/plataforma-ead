package com.plataforma.plataforma_ead.api.dto.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaInput {

	@NotBlank
	private String enunciado;
	
	@Valid
	@NotNull
	private List<PerguntaOpcaoInput> opcoes;
	
}
