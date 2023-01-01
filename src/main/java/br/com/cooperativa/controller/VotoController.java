package br.com.cooperativa.controller;

import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.RegistroVotoRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Voto Controller")
public interface VotoController {
	
	@ApiOperation(value = "Votar em sessão de pauta")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado"),
	        @ApiResponse(code = 422, message = "Erro de validação")
	})
	ResponseEntity<String> votar(RegistroVotoRequestDto votoForm);
	
}
