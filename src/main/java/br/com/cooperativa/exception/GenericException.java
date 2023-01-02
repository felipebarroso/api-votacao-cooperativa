package br.com.cooperativa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = -3940933483978093279L;

	public GenericException(String message) {
        super(message);
    }

}
