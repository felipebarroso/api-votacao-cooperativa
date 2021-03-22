package br.com.cooperativa.exception;

import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntegracaoException extends RuntimeException {

	private static final long serialVersionUID = 6183318458011555448L;

	public IntegracaoException(String message) {
        super(message);
    }

	public IntegracaoException(String message, URISyntaxException e) {
		 super(message, e);
	}
}
