package br.com.cooperativa.exception.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PautaSessaoNaoIniciadaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "A sessão de votação da pauta ainda não está iniciada";
	
	public PautaSessaoNaoIniciadaException() {
		super(ERRO);
	}

}