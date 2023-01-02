package br.com.cooperativa.exception.assembleia;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssembleiaNaoEncontradaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "Assembleia informada não encontada";
	
	public AssembleiaNaoEncontradaException() {
		super(ERRO);
	}

}
