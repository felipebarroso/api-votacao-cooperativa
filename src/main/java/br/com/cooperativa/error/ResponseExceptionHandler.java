package br.com.cooperativa.error;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cooperativa.exception.GenericException;
import br.com.cooperativa.exception.pauta.PautaSessaoEncerradaException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(preencherResponseBody(status, "Requisição inválida"), headers, status);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> genericHandleException(HttpServletRequest req, Exception exception) {
		log.error("Erro não tratado", exception);
		GenericException ex = new GenericException("Erro inesperado");
		log.info("Retornando exceção genérica", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no serviço");
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(PautaSessaoEncerradaException.class)
	public ResponseEntity<Object> handlePautaSessaoEncerradaException(PautaSessaoEncerradaException e) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	private Map<String, Object> preencherResponseBody(HttpStatus status, Object mensagemErro) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", mensagemErro);
		return body;
	}
	
	private ResponseEntity<Object> buildResponseEntity(HttpStatus status, Object mensagemErro) {
		try {
			return ResponseEntity.status(status).body(preencherResponseBody(status, mensagemErro));
		} catch (Exception e) {
			return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
