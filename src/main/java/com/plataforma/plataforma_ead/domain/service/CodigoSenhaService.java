package com.plataforma.plataforma_ead.domain.service;

public interface CodigoSenhaService {
	
	public void salvarCodigo(String email, String code);
	public String obterCodigo(String email);
}
