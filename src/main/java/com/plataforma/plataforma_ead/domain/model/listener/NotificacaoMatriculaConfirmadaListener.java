package com.plataforma.plataforma_ead.domain.model.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.event.MatriculaConfirmadaEvent;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService.Mensagem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacaoMatriculaConfirmadaListener {
	
	private final EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConfirmarMatricula(MatriculaConfirmadaEvent event) {
		
		Matricula matricula = event.getMatricula();
		
		var mensagem = Mensagem.builder()
        		.assunto(matricula.getCurso().getNome() + " - Matricula confirmada")
				.corpo("emails/matricula-confirmada.html")
				.variavel("matricula", matricula)
				.destinatario(matricula.getUsuario().getEmail())
				.build();
        		
		envioEmail.enviar(mensagem);
	}
	
}
