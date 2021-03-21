package br.com.cooperativa.error;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cooperativa.VotacaoCooperativaApplication;
import br.com.cooperativa.exception.GenericException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GenericResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		return new ResponseEntity<>(preencherResponseBody(status, errors), headers, status);

	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(preencherResponseBody(status, "Requisição inválida"), headers, status);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(HttpServletRequest req, Exception exception) throws Exception {
		if(exception instanceof ServletRequestBindingException) {
			log.error("Requisição inválida", exception);
			GenericException err = new GenericException("Requisição inválida");
			log.info("Retornando exceção genérica", err);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
		
		if(!exception.getClass().getPackage().getName().startsWith(VotacaoCooperativaApplication.class.getPackage().getName())) {
			log.error("Erro não tratado", exception);
			GenericException err = new GenericException("Erro inesperado");
			log.info("Retornando exceção genérica", err);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
		
		log.error("Erro no serviço", exception);
		exception.setStackTrace(new StackTraceElement[]{});
		HttpStatus errorStatus = exception.getClass().getAnnotation(ResponseStatus.class).value();
		return ResponseEntity.status(errorStatus).body(preencherResponseBody(errorStatus, exception.getMessage()));
	}
	
	private Map<String, Object> preencherResponseBody(HttpStatus status, Object mensagemErro) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", mensagemErro);
		return body;
	}
	
}
