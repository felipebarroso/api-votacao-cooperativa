package br.com.cooperativa.exception.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PautaNaoEncontradaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "Pauta n�o encontrada";
	
	public PautaNaoEncontradaException() {
		super(ERRO);
	}

}
