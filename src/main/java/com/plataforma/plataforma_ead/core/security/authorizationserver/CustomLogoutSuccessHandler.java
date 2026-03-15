package com.plataforma.plataforma_ead.core.security.authorizationserver;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private final RegisteredClientRepository clientRepository;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String clientId = request.getParameter("client_id");
		String urlAlvo = request.getParameter("post_logout_redirect_uri");

		if (isRedirectAllowed(clientId, urlAlvo)) {
            response.sendRedirect(urlAlvo);
            return;
        }

		response.sendRedirect("/login");
	}
	
	private boolean isRedirectAllowed(String clientId, String urlAlvo) {
        if (clientId == null || urlAlvo == null) {
        	return false;
        } 
        
        RegisteredClient client = clientRepository.findByClientId(clientId);
        
        if (client == null) {
        	return false;
        }

        return client.getPostLogoutRedirectUris().contains(urlAlvo);
    }

}