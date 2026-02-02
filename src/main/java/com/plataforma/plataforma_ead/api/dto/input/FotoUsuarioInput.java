package com.plataforma.plataforma_ead.api.dto.input;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoUsuarioInput {
	
	@Schema(example = "foto-perfil.png")
    @NotNull
    private MultipartFile arquivo;

}