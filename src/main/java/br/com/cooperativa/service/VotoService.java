package br.com.cooperativa.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.GenericException;
import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.RespostaRequisicaoCpfDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.entity.Voto;
import br.com.cooperativa.model.form.RegistroVotoForm;
import br.com.cooperativa.repository.VotoRepository;
import br.com.cooperativa.util.JsonUtil;
import br.com.cooperativa.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VotoService {
	
	@Value("${uri.valida.cpf}")
	private String uriValidacaoCpf;
	
	@Autowired
	private VotoRepository votoRepository;
	
	
	public void processarVotoAssociadoPauta(Pauta pauta, RegistroVotoForm votoForm) {
		validarSeAssociadoJaVotou(pauta, votoForm);
		validarSeAssociadoHatilitado(votoForm);
		registrarVotoAssociadoPauta(pauta, votoForm);
	}
	
	public void validarSeAssociadoHatilitado(RegistroVotoForm votoForm) {
		String endpoint = uriValidacaoCpf.replace("CPF", votoForm.getCpfAssociado());
		
        Optional<RespostaRequisicaoCpfDto> respostaRequisicaoCpfDto = Optional.of(endpoint).flatMap(url -> {
                    try {
                        return RequestUtil.makeRequest(url, null);
                    } catch (GenericException err) {
                        throw new GenericException("Erro ao consultar CPF");
                    }
                })
                .map(resp -> JsonUtil.mapFromJson(resp, RespostaRequisicaoCpfDto.class));
        
        if(!respostaRequisicaoCpfDto.isPresent() || respostaRequisicaoCpfDto.isEmpty() || respostaRequisicaoCpfDto.get().getStatus().equals("UNABLE_TO_VOTE"))
        	throw new UnprocessableEntityException("Associado não está habilitado para votar ou CPF é inválido");
	}
	
	public void validarSeAssociadoJaVotou(Pauta pauta, RegistroVotoForm votoForm) {
		Optional<Voto> votoOp = votoRepository.pesquisarVotoPorAssociadoEPauta(pauta.getId(), pauta.getDataInicioVotacao(), 
				pauta.getDataFimVotacao(), votoForm.getCpfAssociado());
		if(votoOp.isPresent())
			throw new UnprocessableEntityException("Associado já votou nesta pauta");
	}
	
	public void registrarVotoAssociadoPauta(Pauta pauta, @Valid RegistroVotoForm votoForm) {
		Voto voto = votoForm.converterDtoParaVoto();
		votoRepository.save(voto);
	}
	
}
