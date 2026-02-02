package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDTO {
	
	@Schema(example = "1")
    private Long id;

    @Schema(example = "Introdução à Programação")
    private String titulo;

    @Schema(example = "Nesta aula aprenderemos os fundamentos da linguagem.")
    private String descricao;

    @Schema(example = "1")
    private Integer ordem;

    @Schema(example = "https://res.cloudinary.com")
    private String urlVideo;
	
}
