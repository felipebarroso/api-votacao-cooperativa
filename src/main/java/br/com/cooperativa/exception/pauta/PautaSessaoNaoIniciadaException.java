package br.com.cooperativa.exception.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PautaSessaoNaoIniciadaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "A sess�o de vota��o da pauta ainda n�o est� iniciada";
	
	public PautaSessaoNaoIniciadaException() {
		super(ERRO);
	}

}