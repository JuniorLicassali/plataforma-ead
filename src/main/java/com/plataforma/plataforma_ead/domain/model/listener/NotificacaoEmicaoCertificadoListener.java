package com.plataforma.plataforma_ead.domain.model.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.event.CertificadoEmissaoEvent;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService.Mensagem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacaoEmicaoCertificadoListener {

	private final EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConcluirProva(CertificadoEmissaoEvent event) {
		
		Matricula matricula = event.getMatricula();
		
		if (matricula.getQuestionarioUsuario().getFinalizado() == true && matricula.getQuestionarioUsuario().getNota() > 0) {
			var mensagem = Mensagem.builder()
					.assunto("Certificado de conclusão")
					.corpo("emails/certificado-disponivel.html")
					.variavel("matricula", matricula)
					.destinatario(matricula.getUsuario().getEmail())
					.build();
	        		
			envioEmail.enviar(mensagem);
		}
	}
	
}
