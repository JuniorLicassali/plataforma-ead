package com.plataforma.plataforma_ead.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("ead.email")
public class EmailProperties {

	private Implementacao impl = Implementacao.FAKE;
	
	@NotNull
	private String remetente;
	
	public enum Implementacao {
		SMTP, FAKE
	}
	
}