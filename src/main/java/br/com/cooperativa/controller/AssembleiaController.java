package br.com.cooperativa.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.AssembleiaDto;
import br.com.cooperativa.model.form.AssembleiaForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Assembleia Controller")
public interface AssembleiaController {
	
	@ApiOperation(value = "Pesquisar assembleias com paginação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro não encontrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado"),
	        @ApiResponse(code = 422, message = "Erro de validação")
	})
	Page<AssembleiaDto> pesquisar(Integer page, Integer size);
	
	@ApiOperation(value = "Pesquisar assembleia por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 204, message = "Registro não encontrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado"),
	        @ApiResponse(code = 422, message = "Erro de validação")
	})
	ResponseEntity<AssembleiaDto> pesquisar(Long id);
	
	@ApiOperation(value = "Cadastrar assembleia")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso"),
	        @ApiResponse(code = 201, message = "Cadastrado"),
	        @ApiResponse(code = 400, message = "Requisição inválida"),
	        @ApiResponse(code = 401, message = "Não autorizado"),
	        @ApiResponse(code = 404, message = "Recurso não encontrado"),
	        @ApiResponse(code = 422, message = "Erro de validação")
	})
	ResponseEntity<AssembleiaDto> cadastrar(AssembleiaForm assembleiaForm);

}
