package br.com.cooperativa.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.controller.PautaController;
import br.com.cooperativa.model.dto.InicioPautaRequestDto;
import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.dto.PautaRequestDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.service.PautaService;

@RestController
@RequestMapping("/pautas")
public class PautaControllerImpl implements PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	
	@GetMapping("/v1.0")
	public Page<PautaDto> pesquisar(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, 
			@RequestParam(value = "size", defaultValue = "10", required = false)  Integer size) {
		final Page<Pauta> pautas = pautaRepository.findAll(PageRequest.of(page, size));
		return PautaDto.converterParaDto(pautas);
	}
	
	@GetMapping("/v1.0/{id}")
	public ResponseEntity<PautaDto> pesquisar(@PathVariable Long id) {
		final Pauta pauta = pautaService.pesquisarPautaPorId(id);
		return ResponseEntity.ok(new PautaDto(pauta));
	}
	
	@PostMapping("/v1.0")
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaRequestDto pautaRequestDto) {
		final Pauta pauta = pautaService.cadastrar(pautaRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new PautaDto(pauta));
	}
	
	@PutMapping("/v1.0/inicia")
	public ResponseEntity<PautaDto> iniciarSessao(@RequestBody @Valid InicioPautaRequestDto inicioPautaRequestDto) {
		final Pauta pauta = pautaService.iniciarSessaoVotacaoPauta(inicioPautaRequestDto);
		final PautaDto pautaDto = new PautaDto(pauta);
		return ResponseEntity.ok(pautaDto);
	}
	
	@PutMapping("/v1.0/contabiliza")
	public ResponseEntity<List<PautaDto>> contabilizarVotos() {
		final List<PautaDto> pautasEncerradas = pautaService.pesquisarPautasEncerradasParaContabilizarVotos();
		return ResponseEntity.ok(pautasEncerradas);
	}
	
}
