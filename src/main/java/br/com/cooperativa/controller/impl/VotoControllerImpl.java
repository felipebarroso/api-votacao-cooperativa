package br.com.cooperativa.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.controller.VotoController;
import br.com.cooperativa.model.dto.RegistroVotoRequestDto;
import br.com.cooperativa.service.VotoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/votos")
@Slf4j
public class VotoControllerImpl implements VotoController {
	
	@Autowired
	private VotoService votoService;
	
	
	@PostMapping("/v1.0")
	public ResponseEntity<String> votar(@RequestBody @Valid RegistroVotoRequestDto registroVotoRequestDto) {
		log.info("votar");
		votoService.processarVotoDoAssociadoNaPauta(registroVotoRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Seu voto foi registrado com sucesso.");
	}
	
}
