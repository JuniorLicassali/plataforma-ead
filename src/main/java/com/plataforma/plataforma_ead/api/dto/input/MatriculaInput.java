package com.plataforma.plataforma_ead.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatriculaInput {

//	private UsuarioIdInput usuario;
//	private CursoIdInput curso;
	
	//@NotNull
	//@Schema(example = "1")
    private Long usuarioId;
    
    @Schema(example = "1")
    @NotNull
    private Long cursoId; 
	
}