package br.com.cooperativa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.NotFoundException;
import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.PautaDto;
import br.com.cooperativa.model.dto.QuantidadeVotosDto;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.form.InicioPautaForm;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PautaService {
	
	@Value("${pauta.sessao.duracao.minutos}")
	private Long duracaoSessaoPadraoEmMinutos;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	
	public Pauta iniciarSessaoVotacaoPauta(@Valid InicioPautaForm inicioPautaForm) {
		return pesquisarPautaPorId(inicioPautaForm.getPautaId())
			.map(pauta -> validarSeSessaoPodeSerIniciada(pauta))
			.map(pauta -> preencherDadosParaIniciarSessao(pauta, inicioPautaForm))
			.map(pauta -> pautaRepository.save(pauta))
			.orElseThrow(() -> new NotFoundException("Pauta não encontrada"));
	}
	
	public Optional<Pauta> pesquisarPautaPorId(Long id) {
		return pautaRepository.findById(id)
				.map(pauta -> contabilizarVotosSeSessaoEncerrada(pauta));
	}
	
	public Pauta validarSeSessaoPodeSerIniciada(Pauta pauta) {
		validarSeSessaoEncerrada(pauta);
		validarSeSessaoIniciada(pauta);
		return pauta;
	}
	
	public Pauta validarSeSessaoPodeSerVotada(Pauta pauta) {
		validarSeSessaoNaoIniciada(pauta);
		validarSeSessaoEncerrada(pauta);
		return pauta;
	}
	
	public void validarSeSessaoNaoIniciada(Pauta pauta) {
		if(pauta.getDataInicioVotacao() == null || pauta.getDataInicioVotacao().isAfter(LocalDateTime.now()))
			throw new UnprocessableEntityException("A sessão de votação da pauta ainda não está iniciada");
	}
	
	public void validarSeSessaoIniciada(Pauta pauta) {
		if(pauta.getDataInicioVotacao() != null)
			throw new UnprocessableEntityException("A sessão de votação da pauta já está iniciada");
	}
	
	public void validarSeSessaoEncerrada(Pauta pauta) {
		if(verificarSeSessaoEncerrada(pauta))
			throw new UnprocessableEntityException("A sessão de votação da pauta já está encerrada");
	}
	
	public boolean verificarSeSessaoEncerrada(Pauta pauta) {
		if(pauta.getDataFimVotacao() != null && pauta.getDataFimVotacao().isBefore(LocalDateTime.now()))
			return true;
		else
			return false;
	}
	
	private Pauta preencherDadosParaIniciarSessao(Pauta pauta, InicioPautaForm inicioPautaForm) {
		final Long duracaoSessaoPauta = inicioPautaForm.getDuracaoSessaoEmMinutos() != null 
				&& inicioPautaForm.getDuracaoSessaoEmMinutos() > 0L ? inicioPautaForm.getDuracaoSessaoEmMinutos() : this.duracaoSessaoPadraoEmMinutos;
		pauta.setDataInicioVotacao(LocalDateTime.now());
		pauta.setDataFimVotacao(LocalDateTime.now().plusMinutes(duracaoSessaoPauta));
		return pauta;
	}
	
	public Pauta contabilizarVotosSeSessaoEncerrada(Pauta pauta) {
		if(verificarSeSessaoEncerrada(pauta) && pauta.getQuantidadeVotosNao() == null && pauta.getQuantidadeVotosSim() == null) {
			
			final QuantidadeVotosDto quantidadeVotosDto = votoRepository.pesquisarQuantidadeDeVotosPorPautaFinalizada(pauta.getId(), 
					pauta.getDataInicioVotacao(), pauta.getDataFimVotacao());
			
			pauta.setQuantidadeVotosSim(quantidadeVotosDto.getQuantidadeVotosSim());
			pauta.setQuantidadeVotosNao(quantidadeVotosDto.getQuantidadeVotosNao());
			
			return pautaRepository.save(pauta);
			
		} else {
			return pauta;
		}
	}

	public List<PautaDto> pesquisarPautasEncerradasParaContabilizarVotos() {
		List<Pauta> pautasParaEncerramento = pautaRepository.pesquisarPautasFinalizadasParaEncerramento(LocalDateTime.now());
		return pautasParaEncerramento.stream()
			.map(pauta -> contabilizarVotosSeSessaoEncerrada(pauta))
			.map(pauta -> new PautaDto(pauta))
			.collect(Collectors.toList());
	}
	
}
