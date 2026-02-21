package com.plataforma.plataforma_ead.domain.service;

import java.security.SecureRandom;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_ead.api.dto.input.EsqueciSenhaInput;
import com.plataforma.plataforma_ead.api.dto.input.NovaSenhaInput;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.model.event.EsqueciSenhaEvent;
import com.plataforma.plataforma_ead.infrastructure.service.GerarCodigoSenhaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoSenhaService {

	private final GerarCodigoSenhaService redisService;
    private final ApplicationEventPublisher eventPublisher;
    private final CadastroUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    

    public void solicitar(String email) {
        String codigo = gerarCodigoAleatorio();
        
        redisService.salvarCodigo(email, codigo);

        EsqueciSenhaInput input = new EsqueciSenhaInput();
        input.setEmail(email);
        input.setCodigo(codigo);

        eventPublisher.publishEvent(new EsqueciSenhaEvent(input));
    }
    
    @Transactional
    public void alterarComCodigo(NovaSenhaInput input) {
    	String codigoSalvo = redisService.obterCodigo(input.getEmail());
    	
    	if (codigoSalvo == null || !codigoSalvo.equals(input.getCodigo())) {
            throw new NegocioException("Código de verificação inválido ou expirado.");
        }
    	
    	Usuario usuario = usuarioService.buscarPorEmailOuFalhar(input.getEmail());
    	
    	usuario.setSenha(passwordEncoder.encode(input.getNovaSenha()));
    	
    	usuarioService.salvar(usuario);
    }
    
    
    private String gerarCodigoAleatorio() {
	    SecureRandom random = new SecureRandom();
	    int numero = 100000 + random.nextInt(900000);
	    return String.valueOf(numero);
	}
    
}