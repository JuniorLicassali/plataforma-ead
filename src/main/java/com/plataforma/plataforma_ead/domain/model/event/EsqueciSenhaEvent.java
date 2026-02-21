package com.plataforma.plataforma_ead.domain.model.event;

import com.plataforma.plataforma_ead.api.dto.input.EsqueciSenhaInput;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EsqueciSenhaEvent {
	
	private EsqueciSenhaInput esqueciSenha;

}
