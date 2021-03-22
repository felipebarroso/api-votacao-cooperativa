package br.com.cooperativa.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.cooperativa.exception.GenericException;
import br.com.cooperativa.exception.IntegracaoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestUtil {
	
	public static Optional<String> makeRequest(String baseUrl, Map<String, String> mapHeaders) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(baseUrl);
			HttpHeaders headers = new HttpHeaders();
			if(mapHeaders != null && !mapHeaders.isEmpty()) {
				headers.setAll(mapHeaders);
			}
			HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
			if(result.getStatusCodeValue() == HttpStatus.OK.value()) {
				return Optional.of(result.getBody());
			}
			if(result.getStatusCodeValue() == HttpStatus.NO_CONTENT.value()) {
				return Optional.empty();
			}
		} catch (HttpClientErrorException ex) {
			log.error("Erro em requisição à URL " + baseUrl, ex);
			HttpStatus status = ex.getStatusCode();
		    if (status == HttpStatus.NOT_FOUND)
		    	return Optional.empty();
		} catch (URISyntaxException ex) {
			log.error("Erro em requisição à URL " + baseUrl, ex);
			throw new IntegracaoException("Erro em requisição");
		}
		throw new GenericException("Erro em requisição");
	}

}
