package br.com.cooperativa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;

@Service
public class VotoService {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	
	public void processarVotoAssociadoPauta(Pauta pauta, String cpf) {
		validarVotoAssociadoPorPauta(pauta, cpf);
		// TODO
	}
	
	public void validarVotoAssociadoPorPauta(Pauta pauta, String cpf) {
//		validarVotoSeAssociadoPodeVotar(cpf);
//		validarVotoSeAssociadoJaVotou(cpf);
//		validarSeSessaoNaoIniciada(pauta);
//		validarVotoSeSessaoPautaEncerrada(pauta);
	}
	
	public void validarSeAssociadoPodeVotar(String cpf) {
		// TODO
	}
	
	public void validarSeAssociadoJaVotou(String cpf) {
		// TODO
	}
	
	public void registrarVotoAssociadoPauta(Pauta pauta, String cpf) {
		// TODO
	}
	
}
