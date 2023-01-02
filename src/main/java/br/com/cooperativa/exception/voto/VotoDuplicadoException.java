package br.com.cooperativa.exception.voto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotoDuplicadoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "Associado ja votou nesta pauta";
	
	public VotoDuplicadoException() {
		super(ERRO);
	}

}