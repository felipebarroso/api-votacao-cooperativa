package br.com.cooperativa.controller.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.controller.VotoController;
import br.com.cooperativa.exception.NotFoundException;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.form.RegistroVotoForm;
import br.com.cooperativa.service.PautaService;
import br.com.cooperativa.service.VotoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/votos")
@Slf4j
public class VotoControllerImpl implements VotoController {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private VotoService votoService;
	
	
	@PostMapping("/v1.0")
	public ResponseEntity<String> votar(@RequestBody @Valid RegistroVotoForm votoForm) {
		log.info("votar");
		Optional<Pauta> pautaOp = pautaService.pesquisarPautaPorId(votoForm.getPautaId());
		
		if(!pautaOp.isPresent())
			throw new NotFoundException("Pauta informada não encontada");
		
		final Pauta pauta = pautaOp.get();
		pautaService.validarSeSessaoPodeSerVotada(pauta);
		votoService.registrarVotoDoAssociadoNaPauta(pauta, votoForm);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Seu voto foi registrado e seráo processado. Aguarde a confirmação por e-mail.");
	}
	
}
