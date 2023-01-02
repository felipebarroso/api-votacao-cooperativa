package br.com.cooperativa.exception.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PautaSessaoIniciadaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "A sess�o de vota��o da pauta j� est� iniciada";
	
	public PautaSessaoIniciadaException() {
		super(ERRO);
	}

}