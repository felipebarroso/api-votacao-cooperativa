package br.com.cooperativa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.NotFoundException;
import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.InicioPautaRequestDto;
import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.dto.QuantidadeVotosDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PautaService {
	
	@Value("${pauta.sessao.duracao.minutos}")
	private Integer duracaoSessaoPadraoEmMinutos;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	
	public Pauta iniciarSessaoVotacaoPauta(final InicioPautaRequestDto inicioPautaForm) {
		log.info("iniciarSessaoVotacaoPauta");
		return pesquisarPautaPorId(inicioPautaForm.getPautaId())
			.map(pauta -> validarSeSessaoPodeSerIniciada(pauta))
			.map(pauta -> preencherDadosDaPautaParaIniciarSessao(pauta, inicioPautaForm))
			.map(pauta -> pautaRepository.save(pauta))
			.orElseThrow(() -> new NotFoundException("Pauta não encontrada"));
	}
	
	public Optional<Pauta> pesquisarPautaPorId(final Long id) {
		log.info("pesquisarPautaPorId");
		return pautaRepository.findById(id)
				.map(pauta -> contabilizarVotosDaSessaoSeProcessoFinalizado(pauta));
	}
	
	public Pauta validarSeSessaoPodeSerIniciada(final Pauta pauta) {
		validarSeSessaoEncerrada(pauta);
		validarSeSessaoIniciada(pauta);
		return pauta;
	}
	
	// TODO
	// mover validacoes de sessao para dentro da Pauta
	
	public Pauta validarSeSessaoPodeSerVotada(final Pauta pauta) {
		validarSeSessaoNaoIniciada(pauta);
		validarSeSessaoEncerrada(pauta);
		return pauta;
	}
	
	public void validarSeSessaoNaoIniciada(final Pauta pauta) {
		log.info("validarSeSessaoNaoIniciada");
		if(!pauta.sessaoInicada())
			throw new UnprocessableEntityException("A sessão de votação da pauta ainda não está iniciada");
	}
	
	public void validarSeSessaoIniciada(final Pauta pauta) {
		log.info("validarSeSessaoIniciada");
		if(pauta.sessaoInicada())
			throw new UnprocessableEntityException("A sessão de votação da pauta já está iniciada");
	}
	
	public void validarSeSessaoEncerrada(final Pauta pauta) {
		log.info("validarSeSessaoEncerrada");
		if(pauta.sessaoEncerrada())
			throw new UnprocessableEntityException("A sessão de votação da pauta já está encerrada");
	}
	
	private Pauta preencherDadosDaPautaParaIniciarSessao(Pauta pauta, final InicioPautaRequestDto inicioPautaRequestDto) {
		log.info("preencherDadosParaIniciarSessao");
		pauta.setDataInicioVotacao(LocalDateTime.now());
		pauta.setDataFimVotacao(inicioPautaRequestDto.getDuracaoSessaoEmMinutos(), this.duracaoSessaoPadraoEmMinutos);
		return pauta;
	}
	
	public Pauta contabilizarVotosDaSessaoSeProcessoFinalizado(Pauta pauta) {
		log.info("contabilizarVotosDaSessaoSeProcessoFinalizado");
		if(pauta.podeContabilizarVotosDaSessao()) {
			log.info(" - Contabilizando Votos da Pauta ?", pauta.getId());
			final QuantidadeVotosDto quantidadeVotosDto = votoRepository.pesquisarQuantidadeDeVotosPorPautaFinalizada(pauta.getId(), 
					pauta.getDataInicioVotacao(), pauta.getDataFimVotacao());
			pauta.setQuantidadeVotosSim(quantidadeVotosDto.getQuantidadeVotosSim());
			pauta.setQuantidadeVotosNao(quantidadeVotosDto.getQuantidadeVotosNao());
			return pautaRepository.save(pauta);
		} else {
			log.info(" - Sessao nao contabilizada");
			return pauta;
		}
	}
	
	public List<PautaDto> pesquisarPautasEncerradasParaContabilizarVotos() {
		List<Pauta> pautasParaEncerramento = pautaRepository.pesquisarPautasFinalizadasParaEncerramento(LocalDateTime.now());
		return pautasParaEncerramento.stream()
				.map(pauta -> contabilizarVotosDaSessaoSeProcessoFinalizado(pauta))
				.map(pauta -> new PautaDto(pauta))
				.collect(Collectors.toList());
	}
	
}
