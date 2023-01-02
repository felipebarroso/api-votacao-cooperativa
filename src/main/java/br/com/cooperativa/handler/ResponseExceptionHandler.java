package br.com.cooperativa.handler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cooperativa.exception.IntegracaoException;
import br.com.cooperativa.exception.assembleia.AssembleiaNaoEncontradaException;
import br.com.cooperativa.exception.pauta.PautaNaoEncontradaException;
import br.com.cooperativa.exception.pauta.PautaSessaoEncerradaException;
import br.com.cooperativa.exception.pauta.PautaSessaoIniciadaException;
import br.com.cooperativa.exception.pauta.PautaSessaoNaoIniciadaException;
import br.com.cooperativa.exception.voto.AssociadoNaoHabilitadoException;
import br.com.cooperativa.exception.voto.VotoDuplicadoException;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MENSAGEM = "Requisição inválida";

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(preencherResponseBody(status, MENSAGEM), headers, status);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e,
	        WebRequest request) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(IntegracaoException.class)
	public ResponseEntity<Object> handleIntegracaoException(IntegracaoException e) {
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}

	@ExceptionHandler(PautaSessaoEncerradaException.class)
	public ResponseEntity<Object> handlePautaSessaoEncerradaException(PautaSessaoEncerradaException e) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
	@ExceptionHandler(PautaNaoEncontradaException.class)
	public ResponseEntity<Object> handlePautaNaoEncontradaException(PautaNaoEncontradaException e) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(AssembleiaNaoEncontradaException.class)
	public ResponseEntity<Object> handleAssembleiaNaoEncontradaException(AssembleiaNaoEncontradaException e) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(PautaSessaoNaoIniciadaException.class)
	public ResponseEntity<Object> handlePautaSessaoNaoIniciadaException(PautaSessaoNaoIniciadaException e) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
	@ExceptionHandler(PautaSessaoIniciadaException.class)
	public ResponseEntity<Object> handlePautaSessaoIniciadaException(PautaSessaoIniciadaException e) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
	@ExceptionHandler(AssociadoNaoHabilitadoException.class)
	public ResponseEntity<Object> handleAssociadoNaoHabilitadoException(AssociadoNaoHabilitadoException e) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
	@ExceptionHandler(VotoDuplicadoException.class)
	public ResponseEntity<Object> handleVotoDuplicadoException(VotoDuplicadoException e) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
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
