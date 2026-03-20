package com.plataforma.plataforma_ead.api.assembler;

import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.StatusMatriculaDTO;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;

@Component
public class StatusMatriculaDTOAssembler {
	
	public StatusMatriculaDTO toDTO(StatusMatricula statusMatricula) {
        StatusMatriculaDTO dto = new StatusMatriculaDTO();
        
        dto.setStatusMatricula(statusMatricula.name()); 
        
        return dto;
    }

	
}