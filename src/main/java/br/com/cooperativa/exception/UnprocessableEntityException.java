package br.com.cooperativa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = 5189952699244530025L;

	public UnprocessableEntityException(String message) {
        super(message);
    }
    
    public UnprocessableEntityException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
