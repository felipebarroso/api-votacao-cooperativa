package br.com.cooperativa.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import br.com.cooperativa.controller.impl.VotoControllerImpl;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.model.form.RegistroVotoForm;
import br.com.cooperativa.service.PautaService;
import br.com.cooperativa.service.VotoService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(controllers = VotoControllerImpl.class)
public class VotoControllerTeste {
	
	@Autowired
	private VotoControllerImpl votoController;
	
	@MockBean
	private VotoService votoService;
	
	@MockBean
	private PautaService pautaService;
	
	private final Long ID = 1L;
	
	
	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(this.votoController);
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
	public void deveVotarPauta() {
		final RegistroVotoForm votoForm = RegistroVotoForm.builder()
				.concordaComPauta(true)
				.cpfAssociado("00000000000")
				.pautaId(ID)
				.build();
		final Optional<Pauta> pautaOp = Optional.of(construirPauta(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5)));
		
		Mockito.when(this.pautaService.pesquisarPautaPorId(votoForm.getPautaId())).thenReturn(pautaOp);
		Mockito.when(this.pautaService.validarSeSessaoPodeSerVotada(pautaOp.get())).thenReturn(pautaOp.get());
		
		RestAssuredMockMvc
			.given()
				.body(votoForm)
				.contentType(ContentType.JSON)
			.when()
				.post("/votos/v1.0")
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}

}
