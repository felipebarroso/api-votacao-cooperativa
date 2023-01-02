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

import br.com.cooperativa.exception.pauta.PautaSessaoEncerradaException;
import br.com.cooperativa.exception.pauta.PautaSessaoIniciadaException;
import br.com.cooperativa.exception.pauta.PautaSessaoNaoIniciadaException;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.repository.VotoRepository;

public class ValidacaoPautaTest {
	
	@MockBean
	PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);

	@MockBean
	VotoRepository votoRepository = Mockito.mock(VotoRepository.class);
	
	@MockBean
	AssembleiaService assembleiaService = Mockito.mock(AssembleiaService.class);
	
	private PautaService pautaService;
	
	private static final Long ID = 1L;
	private static final Integer DURACAO_SESSAO_PADRAO_EM_MINUTOS = 1;
	
	
	@BeforeEach
	public void beforeEach() {
		this.pautaService = new PautaService(DURACAO_SESSAO_PADRAO_EM_MINUTOS, pautaRepository, votoRepository, assembleiaService);
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
		pauta = pautaService.pesquisarPautaPorId(ID);
		
		assertTrue(pauta.getId() == ID);
	}
	
	@Test
	public void devePermitirIniciarSessaoPauta() {
		try {
			Pauta pauta = construirPauta(null, null);
			pautaService.validarSeSessaoPodeSerIniciada(pauta);
		} catch (PautaSessaoEncerradaException | PautaSessaoIniciadaException e) {
			fail();
		}
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirIniciarSessaoPauta() {
		PautaSessaoIniciadaException exception = assertThrows(PautaSessaoIniciadaException.class, () -> {
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
		} catch (PautaSessaoEncerradaException | PautaSessaoNaoIniciadaException e) {
			fail();
		}
		
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaEncerrada() {
		PautaSessaoEncerradaException exception = assertThrows(PautaSessaoEncerradaException.class, () -> {
			Pauta pauta = construirPauta(LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1));
			pautaService.validarSeSessaoPodeSerVotada(pauta);
		});

	    assertTrue(exception != null ? true : false);
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaNaoIniciada() {
		PautaSessaoNaoIniciadaException exception = assertThrows(PautaSessaoNaoIniciadaException.class, () -> {
			Pauta pauta = construirPauta(null, null);
			pautaService.validarSeSessaoPodeSerVotada(pauta);
		});

	    assertTrue(exception != null ? true : false);
		
	}

}
