package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerguntaOpcaoDTO {
	
	@Schema(example = "A configuração automática de dependências.")
    private String texto;

}
