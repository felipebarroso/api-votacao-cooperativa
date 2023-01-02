package br.com.cooperativa.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.assembleia.AssembleiaNaoEncontradaException;
import br.com.cooperativa.model.dto.AssembleiaRequestDto;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.repository.AssembleiaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AssembleiaService {
	
	@Autowired
	private AssembleiaRepository assembleiaRepository;
	
	
	public Assembleia pesquisarAssembleiaPorId(final Long id) {
		log.info("pesquisarAssembleiaPorId");
		return assembleiaRepository.findById(id)
				.orElseThrow(() -> new AssembleiaNaoEncontradaException());
	}

	public Assembleia cadastrarAssembleia(@Valid AssembleiaRequestDto assembleiaRequestDto) {
		Assembleia assembleia = assembleiaRequestDto.converterDtoParaAssembleia();
		assembleiaRepository.save(assembleia);
		return assembleia;
	}

}
