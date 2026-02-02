package com.plataforma.plataforma_ead.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuloDTO {


    @Schema(example = "1")
    private Long id;

    @Schema(example = "Fundamentos do Spring Boot")
    private String nome;

    @Schema(example = "Neste módulo exploramos a configuração inicial e injeção de dependências.")
    private String descricao;
    
	private List<AulaDTO> aulas;
	
}
