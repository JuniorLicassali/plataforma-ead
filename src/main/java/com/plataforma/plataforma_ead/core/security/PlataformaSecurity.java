package com.plataforma.plataforma_ead.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlataformaSecurity {
	
	private final MatriculaRepository matriculaRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isAutenticado() {
		return getAuthentication().isAuthenticated();
	}

	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();

		Object usuarioId = jwt.getClaim("usuario_id");

		if (usuarioId == null) {
			return null;
		}

		return Long.valueOf(usuarioId.toString());
	}
	
	public boolean podeConsultarSendoAlunoOuAdmn() {
		return matriculaRepository.podeConsultar(getUsuarioId());
	}
	
	public boolean podeConsultarMatrticula(Long cursoId) {
		if (cursoId == null) {
			return false;
		}
		return matriculaRepository.podeConsultarMatrticula(cursoId, getUsuarioId());
	}

	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}

	public boolean temEscopoEscrita() {
		return hasAuthority("SCOPE_WRITE");
	}

	public boolean temEscopoLeitura() {
		return hasAuthority("SCOPE_READ");
	}

	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
	}

	public boolean podeGerenciarCursos() {
		return temEscopoEscrita() && (hasAuthority("EDITAR_CURSOS"));
	}

	public boolean podeConsultarCursos(Long cursoId) {
		
		if (hasAuthority("EDITAR_CURSOS")) {
	        return true;
	    }

	    return isAutenticado() 
	            && cursoId != null 
	            && matriculaRepository.podeAcessarCurso(cursoId, getUsuarioId());
	}

	public boolean podeGerenciarFuncionamentoCurso() {
		return temEscopoEscrita() && (hasAuthority("EDITAR_CURSOS"));
	}

	public boolean podeConsultarUsuariosGruposPermissoes() {
		return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podeEditarUsuariosGruposPermissoes() {
		return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podePesquisarPedidos(Long clienteId) {
		return temEscopoLeitura() && (hasAuthority("CONSULTAR_MATRICULAS") || usuarioAutenticadoIgual(clienteId));
	}

	public boolean podeConsultarFormasPagamento() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarMatricula(Long cursoId) {
		return temEscopoLeitura() && (hasAuthority("CONSULTAR_MATRICULAS") || podeConsultarMatricula(cursoId));
	}

}
