package br.com.cooperativa.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.InicioPautaRequestDto;
import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.dto.PautaRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Pauta Controller")
public interface PautaController {
	
	@ApiOperation(value = "Pesquisar pautas com pagina��o")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro n�o encontrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	Page<PautaDto> pesquisar(Integer page, Integer size);
	
	@ApiOperation(value = "Pesquisar pauta por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro n�o encontrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	ResponseEntity<PautaDto> pesquisar(Long id);
	
	@ApiOperation(value = "Cadastrar pauta")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	ResponseEntity<PautaDto> cadastrar(PautaRequestDto pautaForm);
	
	@ApiOperation(value = "Iniciar sess�o de vota��o da pauta")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	ResponseEntity<PautaDto> iniciarSessao(InicioPautaRequestDto inicioPautaForm);
	
	@ApiOperation(value = "Contabilizar votos das pautas encerradas")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisi��o inv�lida"),
	        @ApiResponse(code = 401, message = "N�o autorizado"),
	        @ApiResponse(code = 404, message = "Recurso n�o encontrado"),
	        @ApiResponse(code = 422, message = "Erro de valida��o")
	})
	public ResponseEntity<List<PautaDto>> contabilizarVotos();

}
