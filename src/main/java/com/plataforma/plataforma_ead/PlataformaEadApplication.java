package com.plataforma.plataforma_ead;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.plataforma.plataforma_ead.core.io.Base64ProtocolResolver;

@EnableAsync
@SpringBootApplication
public class PlataformaEadApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		
		var app = new SpringApplication(PlataformaEadApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
		
	}

}
