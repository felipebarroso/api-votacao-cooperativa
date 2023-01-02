package br.com.cooperativa.exception.voto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AssociadoNaoHabilitadoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "Associado n�o est� habilitado para votar ou CPF � inv�lido";
	
	public AssociadoNaoHabilitadoException() {
		super(ERRO);
	}

}