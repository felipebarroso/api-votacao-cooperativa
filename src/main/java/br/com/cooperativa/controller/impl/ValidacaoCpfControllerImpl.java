package br.com.cooperativa.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.controller.ValidacaoCpfController;
import br.com.cooperativa.model.dto.SimulacaoValidacaoCpfDto;

@RestController
@RequestMapping("/cpf")
public class ValidacaoCpfControllerImpl implements ValidacaoCpfController {
	
	@GetMapping("/v1.0/{cpf}")
	public ResponseEntity<SimulacaoValidacaoCpfDto> simulaValidacaoCpf(@PathVariable String cpf) {
		return ResponseEntity.status(HttpStatus.OK).body(SimulacaoValidacaoCpfDto.builder().status("ABLE_TO_VOTE").build());
	}
	
}
