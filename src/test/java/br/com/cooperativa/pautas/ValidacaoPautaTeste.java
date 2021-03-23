package br.com.cooperativa.pautas;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.com.cooperativa.exception.UnprocessableEntityException;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.service.PautaService;

public class ValidacaoPautaTeste {
	
	@Test
	public void devePermitirIniciarSessaoPauta() {
		try {
			
			Pauta pauta = Pauta.builder()
					.assembleia(Assembleia.builder().id(1L).descricao("Assembleia teste").build())
					.dataInicioVotacao(null)
					.dataFimVotacao(null)
					.descricao("Pauta teste")
					.id(1L)
					.build();
			
			PautaService pautaService = new PautaService();
			pautaService.validarSeSessaoPodeSerIniciada(pauta);
			
		} catch (UnprocessableEntityException e) {
			fail();
		}
		
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirIniciarSessaoPauta() {
		
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			
			Pauta pauta = Pauta.builder()
					.assembleia(Assembleia.builder().id(1L).descricao("Assembleia teste").build())
					.dataInicioVotacao(LocalDateTime.now())
					.dataFimVotacao(LocalDateTime.now().plusMinutes(5))
					.descricao("Pauta teste")
					.id(1L)
					.build();
			
			PautaService pautaService = new PautaService();
			pautaService.validarSeSessaoPodeSerIniciada(pauta);
			
		});

	    assertTrue(exception != null ? true : false);
		
	}
	
	@Test
	public void devePermitirVotarSessaoPauta() {
		try {
			
			Pauta pauta = Pauta.builder()
					.assembleia(Assembleia.builder().id(1L).descricao("Assembleia teste").build())
					.dataInicioVotacao(LocalDateTime.now())
					.dataFimVotacao(LocalDateTime.now().plusMinutes(5))
					.descricao("Pauta teste")
					.id(1L)
					.build();
			
			PautaService pautaService = new PautaService();
			pautaService.validarSeSessaoPodeSerVotada(pauta);
			
		} catch (UnprocessableEntityException e) {
			fail();
		}
		
		assertTrue(true);
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaEncerrada() {
		
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			
			Pauta pauta = Pauta.builder()
					.assembleia(Assembleia.builder().id(1L).descricao("Assembleia teste").build())
					.dataInicioVotacao(LocalDateTime.now().minusMinutes(5))
					.dataFimVotacao(LocalDateTime.now().minusMinutes(1))
					.descricao("Pauta teste")
					.id(1L)
					.build();
			
			PautaService pautaService = new PautaService();
			pautaService.validarSeSessaoPodeSerVotada(pauta);
			
		});

	    assertTrue(exception != null ? true : false);
		
	}
	
	@Test
	public void naoDevePermitirVotarSessaoPautaNaoIniciada() {
		
		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> {
			
			Pauta pauta = Pauta.builder()
					.assembleia(Assembleia.builder().id(1L).descricao("Assembleia teste").build())
					.dataInicioVotacao(null)
					.dataFimVotacao(null)
					.descricao("Pauta teste")
					.id(1L)
					.build();
			
			PautaService pautaService = new PautaService();
			pautaService.validarSeSessaoPodeSerVotada(pauta);
			
		});

	    assertTrue(exception != null ? true : false);
		
	}

}
