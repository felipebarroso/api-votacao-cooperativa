package br.com.cooperativa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.RespostaRequisicaoCpfDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.entity.Voto;
import br.com.cooperativa.model.form.RegistroVotoForm;
import br.com.cooperativa.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class VotoService {
	
	@Value("${uri.valida.cpf}")
	private String uriValidacaoCpf;
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private CpfService cpfService;
	
	
	public void registrarVotoDoAssociadoNaPauta(Pauta pauta, RegistroVotoForm votoForm) {
		validarSeAssociadoJaVotou(pauta, votoForm);
		// TODO
		validarSeAssociadoHatilitado(votoForm); // retirar validacao desse processo
		// inserir voto na fila de votacao
		registrarVotoNaoValidadoNaPauta(votoForm);
	}
	
	public void validarSeAssociadoHatilitado(RegistroVotoForm votoForm) {
		log.info("validarSeAssociadoHatilitado");
//        Optional<RespostaRequisicaoCpfDto> respostaRequisicaoCpfDto = cpfService.validarCpfAssociado(votoForm);
//        if(!respostaRequisicaoCpfDto.isPresent() || respostaRequisicaoCpfDto.isEmpty() || respostaRequisicaoCpfDto.get().getStatus().equals("UNABLE_TO_VOTE"))
//        	throw new UnprocessableEntityException("Associado não está habilitado para votar ou CPF é inválido");
	}
	
	public void validarSeAssociadoJaVotou(Pauta pauta, RegistroVotoForm votoForm) {
		log.info("validarSeAssociadoJaVotou");
		Optional<Voto> votoOp = votoRepository.pesquisarVotoPorAssociadoEPauta(pauta.getId(), pauta.getDataInicioVotacao(), 
				pauta.getDataFimVotacao(), votoForm.getCpfAssociado());
		if(votoOp.isPresent())
			throw new UnprocessableEntityException("Associado ja votou nesta pauta");
	}
	
	public void registrarVotoNaoValidadoNaPauta(RegistroVotoForm votoForm) {
		log.info("registrarVotoNaoValidadoNaPauta");
		Voto voto = votoForm.converterDtoParaVoto();
		votoRepository.save(voto);
	}
	
}
