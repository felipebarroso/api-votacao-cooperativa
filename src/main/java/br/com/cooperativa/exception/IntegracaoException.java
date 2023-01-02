package br.com.cooperativa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntegracaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IntegracaoException(String message) {
        super(message);
        this.setStackTrace(new StackTraceElement[]{});
    }

}
