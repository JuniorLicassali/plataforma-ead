package com.plataforma.plataforma_ead.core.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;
import com.plataforma.plataforma_ead.infrastructure.email.FakeEnvioEmailService;
import com.plataforma.plataforma_ead.infrastructure.email.ProcessadorEmailTemplate;
import com.plataforma.plataforma_ead.infrastructure.email.SmtpEnvioEmailService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

	private final EmailProperties emailProperties;
	private final JavaMailSender mailSender;
    private final ProcessadorEmailTemplate processadorEmailTemplate;

	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService(processadorEmailTemplate);
			case SMTP:
				return new SmtpEnvioEmailService(mailSender, emailProperties, processadorEmailTemplate);
			default:
				return null;
		}
	}

}