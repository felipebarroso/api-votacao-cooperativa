package br.com.cooperativa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.RegistroVotoRequestDto;
import br.com.cooperativa.model.dto.RespostaRequisicaoCpfDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.entity.Voto;
import br.com.cooperativa.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private CpfService cpfService;
	
	
	public void registrarVotoDoAssociadoNaPauta(final Pauta pauta, final RegistroVotoRequestDto votoForm) {
		validarSeAssociadoJaVotou(pauta, votoForm);
		validarSeAssociadoHatilitado(votoForm);
		registrarVotoNaPauta(votoForm);
	}
	
	public void validarSeAssociadoHatilitado(final RegistroVotoRequestDto votoForm) {
		log.info("validarSeAssociadoHatilitado");
		final Optional<RespostaRequisicaoCpfDto> respostaRequisicaoCpfDto = cpfService.validarCpfAssociado(votoForm);
        if(!respostaRequisicaoCpfDto.isPresent() || respostaRequisicaoCpfDto.isEmpty() || respostaRequisicaoCpfDto.get().getStatus().equals("UNABLE_TO_VOTE"))
        	throw new UnprocessableEntityException("Associado não está habilitado para votar ou CPF é inválido");
	}
	
	public void validarSeAssociadoJaVotou(final Pauta pauta, final RegistroVotoRequestDto votoForm) {
		log.info("validarSeAssociadoJaVotou");
		final Optional<Voto> votoOp = votoRepository.pesquisarVotoPorAssociadoEPauta(pauta.getId(), pauta.getDataInicioVotacao(), 
				pauta.getDataFimVotacao(), votoForm.getCpfAssociado());
		if(votoOp.isPresent())
			throw new UnprocessableEntityException("Associado ja votou nesta pauta");
	}
	
	public void registrarVotoNaPauta(final RegistroVotoRequestDto votoForm) {
		log.info("registrarVotoNaoValidadoNaPauta");
		final Voto voto = votoForm.converterDtoParaVoto();
		votoRepository.save(voto);
	}
	
}
