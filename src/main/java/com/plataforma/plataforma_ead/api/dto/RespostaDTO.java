package com.plataforma.plataforma_ead.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaDTO {
	
    private Long perguntaId;
    private String resposta;
    private Boolean isCorreta;
    
}