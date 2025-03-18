package com.plataforma.plataforma_ead.domain.exception;

public class QuestionarioExpiradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public QuestionarioExpiradoException(String message) {
        super(message);
    }

}
