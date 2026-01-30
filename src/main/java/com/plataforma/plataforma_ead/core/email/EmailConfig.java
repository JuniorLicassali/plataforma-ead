package com.plataforma.plataforma_ead.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;
import com.plataforma.plataforma_ead.infrastructure.email.FakeEnvioEmailService;
import com.plataforma.plataforma_ead.infrastructure.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			default:
				return null;
		}
	}

}