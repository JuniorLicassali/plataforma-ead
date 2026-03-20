package com.plataforma.plataforma_ead.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusMatriculaDTO {
	
	@Schema(example = "PAGAMENTO_CONFIRMADO")
    private String statusMatricula;
}