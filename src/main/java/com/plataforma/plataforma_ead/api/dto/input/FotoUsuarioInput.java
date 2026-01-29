package com.plataforma.plataforma_ead.api.dto.input;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoUsuarioInput {
	
    @NotNull
    private MultipartFile arquivo;

}