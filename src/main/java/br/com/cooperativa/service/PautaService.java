package br.com.cooperativa.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cooperativa.exception.NotFoundException;
import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.form.InicioPautaForm;
import br.com.cooperativa.repository.PautaRepository;

@Service
public class PautaService {
	
	@Value("${pauta.sessao.duracao.minutos}")
	private Long duracaoSessaoPadraoEmMinutos;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	
	public Pauta iniciarSessaoVotacaoPauta(@Valid InicioPautaForm inicioPautaForm) {
		return pesquisarPautaPorId(inicioPautaForm.getPautaId())
			.map(pauta -> validarSePodeIniciarSessao(pauta))
			.map(pauta -> preencherDadosInicioPauta(pauta, inicioPautaForm))
			.map(pauta -> pautaRepository.save(pauta))
			.orElseThrow(() -> new NotFoundException("Pauta não encontrada"));
	}
	
	public Optional<Pauta> pesquisarPautaPorId(Long id) {
		return pautaRepository.findById(id)
				.map(pauta -> verificarSeDeveContabilizarVotos(pauta));
	}
	
	public Pauta validarSePodeIniciarSessao(Pauta pauta) {
		validarSeSessaoIniciada(pauta);
		validarSeSessaoEncerrada(pauta);
		return pauta;
	}
	
	public void validarSeSessaoNaoIniciada(Pauta pauta) {
		if(pauta.getDataInicioVotacao() == null || pauta.getDataInicioVotacao().isAfter(LocalDateTime.now()))
			throw new UnprocessableEntityException("A pauta ainda não está iniciada");
	}
	
	public void validarSeSessaoIniciada(Pauta pauta) {
		if(pauta.getDataInicioVotacao() != null)
			throw new UnprocessableEntityException("A pauta já está iniciada");
	}
	
	public void validarSeSessaoEncerrada(Pauta pauta) {
		if(pauta.getDataFimVotacao() != null && pauta.getDataFimVotacao().isBefore(LocalDateTime.now()))
			throw new UnprocessableEntityException("A pauta já teve sua sessão encerrada");
	}
	
	private Pauta preencherDadosInicioPauta(Pauta pauta, InicioPautaForm inicioPautaForm) {
		final Long duracaoSessaoPauta = inicioPautaForm.getDuracaoSessaoEmMinutos() != null 
				&& inicioPautaForm.getDuracaoSessaoEmMinutos() > 0L ? inicioPautaForm.getDuracaoSessaoEmMinutos() : this.duracaoSessaoPadraoEmMinutos;
		pauta.setDataInicioVotacao(LocalDateTime.now());
		pauta.setDataFimVotacao(LocalDateTime.now().plusMinutes(duracaoSessaoPauta));
		return pauta;
	}
	
	public Pauta verificarSeDeveContabilizarVotos(Pauta pauta) {
		if(pauta.getDataInicioVotacao() != null && pauta.getDataFimVotacao() != null 
				&& pauta.getDataFimVotacao().isBefore(LocalDateTime.now()) 
				&& pauta.getQuantidadeVotosNao() == null && pauta.getQuantidadeVotosSim() == null)
			contabilizarVotosSessaoPauta(pauta);
		return pauta;
	}
	
	public Pauta contabilizarVotosSessaoPauta(Pauta pauta) {
		// TODO consulta no banco tabela voto retornando duas colunas as somas de quantidadeVotosSim, quantidadeVotosNao
		// set valores na pauta
		// salva pauta
		return pauta;
	}
	
}
