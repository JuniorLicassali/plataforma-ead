package com.plataforma.plataforma_ead.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Curso {
		
		@PreAuthorize("@plataformaSecurity.podeConsultarCursos(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @plataformaSecurity.podeEditarCurso(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
	}
	
	public @interface Aula {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @plataformaSecurity.podeGerenciarCursos()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeConsultarCursos(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("(@plataformaSecurity.podeGerenciarCursos())")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeExcluirAula { }
		
	}
	
	public @interface Matricula {
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeConsultarMatricula(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeListarMatriculas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeListar { }
		
		@PreAuthorize("@plataformaSecurity.podeMatricular(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeMatricular { }
	}
	
	
	public @interface Modulo {
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeConsultarCursos(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @plataformaSecurity.podeEditarCurso(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
	}
	
	
	public @interface Pagamento {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeListarPagamentos()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeListar { }
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated() and @plataformaSecurity.podeConsultarPagamento(#pagamentoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar { }
		
	}
	
	
	public @interface Questionario {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('SCOPE_WRITE') and isAuthenticated() and @plataformaSecurity.podeEditarQuestionario(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('SCOPE_WRITE') and isAuthenticated() and @plataformaSecurity.podeIniciarOuEnviarRespostasAoQuestionario(#cursoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
	}
	
	
	public @interface FotoUsuario {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('SCOPE_WRITE') and isAuthenticated() and @plataformaSecurity.podeEditarFotoUsuario()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
	}
		
	
	
	
	public @interface UsuariosGruposPermissoes {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and "
				+ "@plataformaSecurity.usuarioAutenticadoIgual(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarPropriaSenha { }
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
				+ "@plataformaSecurity.getUsuarioAutenticado(#usuarioId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarUsuario { }
		
		@PreAuthorize("@plataformaSecurity.podeEditarUsuariosGruposPermissoes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
		@PreAuthorize("@plataformaSecurity.podeConsultarUsuariosGruposPermissoes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
	}
	
}