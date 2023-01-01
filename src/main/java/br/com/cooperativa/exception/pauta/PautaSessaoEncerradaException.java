package br.com.cooperativa.exception.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PautaSessaoEncerradaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static String erro = "A sess�o de vota��o da pauta j� est� encerrada";
	
	public PautaSessaoEncerradaException() {
		super(erro);
	}

}
