package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoUsuarioDTO {
	
	@Schema(example = "perfil-usuario-123.jpg")
    private String nomeArquivo;

    @Schema(example = "Foto de perfil do usuário")
    private String descricao;

    @Schema(example = "image/jpeg")
    private String contentType;

    @Schema(example = "204800")
    private Long tamanho;

}