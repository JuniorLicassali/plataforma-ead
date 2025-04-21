package com.plataforma.plataforma_ead.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatriculaInput {

//	private UsuarioIdInput usuario;
//	private CursoIdInput curso;
	
	private Long usuarioId;
	
//	@NotNull
    private Long cursoId; 
	
}