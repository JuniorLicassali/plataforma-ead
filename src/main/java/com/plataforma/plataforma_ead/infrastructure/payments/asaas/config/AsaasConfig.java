package com.plataforma.plataforma_ead.infrastructure.payments.asaas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AsaasConfig {
	
	@Value("${asaas.api.url}")
	private String baseUrl;
	
	@Value("${asaas.api.key}")
	private String apiKey;
	
	@Bean
	public RestTemplate asaasRestTemplate(RestTemplateBuilder builder) {
		return builder
				.rootUri(baseUrl)
				.defaultHeader("Content-Type", "application/json")
                .defaultHeader("access_token", apiKey)
                .defaultHeader("User-Agent", "plataforma-ead-app")
                .build();
	}

}
