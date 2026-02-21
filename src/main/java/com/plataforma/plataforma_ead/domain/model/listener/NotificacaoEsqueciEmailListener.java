package com.plataforma.plataforma_ead.domain.model.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.domain.model.event.EsqueciSenhaEvent;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService.Mensagem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacaoEsqueciEmailListener {

	private final EnvioEmailService envioEmail;

	@Async
	@EventListener
	public void aoSolicitarTrocaSenha(EsqueciSenhaEvent event) {
		var esqueciSenha = event.getEsqueciSenha();

		
			var mensagem = Mensagem.builder().assunto("Código de verificação")
					.corpo("emails/codigo-trocar-senha.html")
					.variavel("codigo", esqueciSenha.getCodigo())
					.destinatario(esqueciSenha.getEmail()).build();

			envioEmail.enviar(mensagem);
	}

}
