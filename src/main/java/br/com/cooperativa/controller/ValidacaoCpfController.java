package br.com.cooperativa.controller;

import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.SimulacaoValidacaoCpfDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Valida��o CPF Controller")
public interface ValidacaoCpfController {
	
	@ApiOperation(value = "Simula valida��o externa de CPF")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	public ResponseEntity<SimulacaoValidacaoCpfDto> simulaValidacaoCpf(String cpf);
	
}
