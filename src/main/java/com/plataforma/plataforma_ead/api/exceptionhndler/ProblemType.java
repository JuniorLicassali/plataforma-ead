package com.plataforma.plataforma_ead.api.exceptionhndler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://plataform.com.br" + path;
		this.title = title;
	}
	
}
