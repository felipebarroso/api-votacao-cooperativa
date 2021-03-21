package br.com.cooperativa.controller.impl;

import java.util.Optional;

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

import br.com.cooperativa.controller.PautaController;
import br.com.cooperativa.exception.NotFoundException;
import br.com.cooperativa.model.dto.AssembleiaDto;
import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.form.PautaForm;
import br.com.cooperativa.repository.AssembleiaRepository;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.service.PautaService;

@RestController
@RequestMapping("/pautas")
public class PautaControllerImpl implements PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private AssembleiaRepository assembleiaRepository;
	
	
	@GetMapping
	public Page<PautaDto> pesquisar(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, 
			@RequestParam(value = "size", defaultValue = "10", required = false)  Integer size) {
		Page<Pauta> pautas = pautaRepository.findAll(PageRequest.of(page, size));
		return PautaDto.converter(pautas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PautaDto> pesquisar(@PathVariable Long id) {
		return pautaRepository.findById(id)
				.map(pauta -> ResponseEntity.ok(new PautaDto(pauta)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaForm pautaForm) {
		Optional<Assembleia> assembleia = assembleiaRepository.findById(pautaForm.getAssembleiaId());
		
		if(!assembleia.isPresent())
			throw new NotFoundException("Assembleia informada não encontada");
		
		Pauta pauta = pautaForm.converter(assembleia.get());
		pautaRepository.save(pauta);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new PautaDto(pauta));
	}
	
}
