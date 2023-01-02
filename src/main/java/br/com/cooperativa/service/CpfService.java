package br.com.cooperativa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.GenericException;
import br.com.cooperativa.model.dto.RegistroVotoRequestDto;
import br.com.cooperativa.model.dto.RespostaRequisicaoCpfDto;
import br.com.cooperativa.util.JsonUtil;
import br.com.cooperativa.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CpfService {
	
	@Value("${url.valida.cpf}")
	private String urlValidacaoCpf;
	
	public Optional<RespostaRequisicaoCpfDto> validarCpfAssociado(final RegistroVotoRequestDto registroVotoRequestDto) {
		log.info("validarCpfAssociado");
		String endpoint = this.urlValidacaoCpf.replace("CPF", registroVotoRequestDto.getCpfAssociado());
		
		final Optional<RespostaRequisicaoCpfDto> respostaRequisicaoCpfDto = Optional.of(endpoint)
				.flatMap(url -> {
                    try {
                        return RequestUtil.makeRequest(url, null);
                    } catch (GenericException err) {
                        throw new GenericException("Erro ao consultar CPF");
                    }
                })
                .map(response -> JsonUtil.mapFromJson(response, RespostaRequisicaoCpfDto.class));
        
        return respostaRequisicaoCpfDto;
	}
	
}
