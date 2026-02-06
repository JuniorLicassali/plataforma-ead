package com.plataforma.plataforma_ead.infrastructure.email;

import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FakeEnvioEmailService implements EnvioEmailService {

	private final ProcessadorEmailTemplate processadorEmailTemplate;
	
	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processadorEmailTemplate.processarTemplate(mensagem);

		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}

}