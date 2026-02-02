package com.plataforma.plataforma_ead.api.dto.input;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaInput {

	@Schema(example = "Qual é o principal objetivo do padrão DTO?")
	@NotBlank
	private String enunciado;
	
	@Valid
	@NotNull
	private List<PerguntaOpcaoInput> opcoes;
	
}
