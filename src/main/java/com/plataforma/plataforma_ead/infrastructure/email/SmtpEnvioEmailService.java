package com.plataforma.plataforma_ead.infrastructure.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.plataforma.plataforma_ead.core.email.EmailProperties;
import com.plataforma.plataforma_ead.domain.service.EnvioEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SmtpEnvioEmailService implements EnvioEmailService {

	private final JavaMailSender mailSender;
	private final EmailProperties emailProperties;
	private final ProcessadorEmailTemplate processadorEmailTemplate;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = criarMimeMessage(mensagem);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}
	
	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processadorEmailTemplate.processarTemplate(mensagem);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);
		
		return mimeMessage;
	}
	
}