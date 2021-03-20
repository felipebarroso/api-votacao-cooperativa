package br.com.cooperativa.controller;

import org.springframework.http.ResponseEntity;

import br.com.cooperativa.model.dto.AssembleiaDto;
import br.com.cooperativa.model.form.AssembleiaForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Assembleia Controller")
public interface AssembleiaController {
	
	@ApiOperation(value = "Pesquisar assembleia pelo id")
	  @ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Sucesso"),
	          @ApiResponse(code = 401, message = "Não autorizado"),
	          @ApiResponse(code = 404, message = "Recurso não encontrado")
	  })
	  ResponseEntity<AssembleiaDto> pesquisar(Long id);
	
	@ApiOperation(value = "Cadastrar assembleia")
	  @ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Sucesso"),
	          @ApiResponse(code = 401, message = "Não autorizado"),
	          @ApiResponse(code = 404, message = "Recurso não encontrado")
	  })
	  ResponseEntity<AssembleiaDto> cadastrar(AssembleiaForm assembleiaForm);

}
