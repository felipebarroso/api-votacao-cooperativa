package br.com.cooperativa.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.form.InicioPautaForm;
import br.com.cooperativa.model.form.PautaForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Pauta Controller")
public interface PautaController {
	
	@ApiOperation(value = "Pesquisar pautas com paginação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro não encontrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado")
	})
	Page<PautaDto> pesquisar(Integer page, Integer size);
	
	@ApiOperation(value = "Pesquisar pauta por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro não encontrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado")
	})
	ResponseEntity<PautaDto> pesquisar(Long id);
	
	@ApiOperation(value = "Cadastrar pauta")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado")
	})
	ResponseEntity<PautaDto> cadastrar(PautaForm pautaForm);
	
	@ApiOperation(value = "Iniciar sessão de votação da pauta")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado"),
	        @ApiResponse(code = 422, message = "Erro de validaçã")
	})
	ResponseEntity<PautaDto> iniciarSessao(InicioPautaForm inicioPautaForm);

}
