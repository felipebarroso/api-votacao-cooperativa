package br.com.cooperativa.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.controller.AssembleiaController;
import br.com.cooperativa.model.dto.AssembleiaDto;
import br.com.cooperativa.model.dto.AssembleiaRequestDto;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.repository.AssembleiaRepository;
import br.com.cooperativa.service.AssembleiaService;

@RestController
@RequestMapping("/assembleias")
public class AssembleiaControllerImpl implements AssembleiaController {
	
	@Autowired
	private AssembleiaRepository assembleiaRepository;
	
	@Autowired
	private AssembleiaService assembleiaService;
	
	
	@GetMapping({"/v1.0", "/v1.1", "/v1.2"})
	public Page<AssembleiaDto> pesquisar(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, 
			@RequestParam(value = "size", defaultValue = "10", required = false)  Integer size) {
		final Page<Assembleia> assembleias = assembleiaRepository.findAll(PageRequest.of(page, size));
		return AssembleiaDto.converterParaDto(assembleias);
	}
	
	@GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
	public ResponseEntity<AssembleiaDto> pesquisar(@PathVariable Long id) {
		final Assembleia assembleia = assembleiaService.pesquisarAssembleiaPorId(id);
		return ResponseEntity.ok(new AssembleiaDto(assembleia));
	}
	
	@PostMapping("/v1.0")
	public ResponseEntity<AssembleiaDto> cadastrar(@RequestBody @Valid AssembleiaRequestDto assembleiaRequestDto) {
		final Assembleia assembleia = assembleiaService.cadastrarAssembleia(assembleiaRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new AssembleiaDto(assembleia));
	}
	
}
