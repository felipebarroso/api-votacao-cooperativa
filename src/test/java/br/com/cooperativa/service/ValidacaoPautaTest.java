package br.com.cooperativa.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.dto.QuantidadeVotosDto;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.repository.VotoRepository;

public class ValidacaoPautaTest {
	
	@MockBean
	PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);

	@MockBean
	VotoRepository votoRepository = Mockito.mock(VotoRepository.class);
	
	private PautaService pautaService;
	
	private final Long ID = 1L;
	
	
	@BeforeEach
	public void beforeEach() {
		this.pautaService = new PautaService(ID, pautaRepository, votoRepository);
	}
	
	private Pauta construirPauta(LocalDateTime dataInicio, LocalDateTime dataFim) {
		return Pauta.builder()
				.assembleia(Assembleia.builder().id(ID).descricao("Assembleia teste").build())
				.dataInicioVotacao(dataInicio)
				.dataFimVotacao(dataFim)
				.descricao("Pauta teste")
				.id(ID)
				.build();
	}
	
	@Test
	public void devePesquisarPautaSemContabilizarVotos() {
		Pauta pauta = construirPauta(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
		
		Mockito.when(pautaRepository.findById(ID)).thenReturn(Optional.of(pauta));
		Optional<Pauta> pautaOp = pautaService.pesquisarPautaPorId(ID);
		
		assertTrue(pautaOp.get().getId() == ID);
	}
	
//	@Test
//	public void devePesquisarPautaEContabilizarVotos() {
//		final Long quantidadeVotosSim = 10L;
//		final Long quantidadeVotosNao = 5L;
//		Pauta pauta = construirPauta(LocalDateTime.now().minusMinutes(6), LocalDateTime.now().minusMinutes(1));
//		final QuantidadeVotosDto quantidadeVotosDto = QuantidadeVotosDto.builder().quantidadeVotosSim(quantidadeVotosSim).quantidadeVotosNao(quantidadeVotosNao).build();
//		
//		Mockito.when(pautaRepository.findById(ID)).thenReturn(Optional.of(pauta));
//		Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);
//		Mockito.when(votoRepository.pesquisarQuantidadeDeVotosPorPautaFinalizada(pauta.getId(), pauta.getDataInicioVotacao(), pauta.getDataFimVotacao())).thenReturn(quantidadeVotosDto);
//		
//		Optional<Pauta> pautaOp = pautaService.pesquisarPautaPorId(ID);
//		
//		assertTrue(pautaOp.get().getQuantidadeVotosSim() != null && pautaOp.get().getQuantidadeVotosSim() == quantidadeVotosSim);
//		assertTrue(pautaOp.get().getQuantidadeVotosNao() != null && pautaOp.get().getQuantidadeVotosNao() == quantidadeVotosNao);
//		Mockito.verify(pautaRepository).save(pauta);
//	}
	
	@Test
	public void devePermitirIniciarSessaoPauta() {
		try {
			Pauta pauta = construirPauta(null, null);
			pautaService.validarSeSessaoPodeSerIniciada(pauta);
		} catch (UnprocessableEntityException e) {
			fail();
		}
		
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirIniciarSessaoPauta() {
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			Pauta pauta = construirPauta(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
			pautaService.validarSeSessaoPodeSerIniciada(pauta);
		});

	    assertTrue(exception != null ? true : false);
		
	}
	
	@Test
	public void devePermitirVotarSessaoPauta() {
		try {
			Pauta pauta = construirPauta(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
			pautaService.validarSeSessaoPodeSerVotada(pauta);
		} catch (UnprocessableEntityException e) {
			fail();
		}
		
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaEncerrada() {
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			Pauta pauta = construirPauta(LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1));
			pautaService.validarSeSessaoPodeSerVotada(pauta);
		});

	    assertTrue(exception != null ? true : false);
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaNaoIniciada() {
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			Pauta pauta = construirPauta(null, null);
			pautaService.validarSeSessaoPodeSerVotada(pauta);
		});

	    assertTrue(exception != null ? true : false);
		
	}

}
