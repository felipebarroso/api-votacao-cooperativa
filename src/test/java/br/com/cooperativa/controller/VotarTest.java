package br.com.cooperativa.controller;

import static org.hamcrest.CoreMatchers.containsString;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.com.cooperativa.model.dto.RegistroVotoRequestDto;
import br.com.cooperativa.model.dto.RespostaRequisicaoCpfDto;
import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
import br.com.cooperativa.repository.PautaRepository;
import br.com.cooperativa.service.CpfService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VotarTest {
	
	@LocalServerPort
	private Integer port;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean
	private CpfService cpfService;
	
	private static final Long ID = 1L;
	
	
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
		final RegistroVotoRequestDto registroVotoRequestDto = RegistroVotoRequestDto.builder()
				.concordaComPauta(true)
				.cpfAssociado("00000000000")
				.pautaId(ID)
				.build();
		final RespostaRequisicaoCpfDto respostaRequisicaoCpfDto = RespostaRequisicaoCpfDto.builder().status("ABLE_TO_VOTE").build();
		final Optional<Pauta> pautaOp = Optional.of(construirPauta(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5)));
		
		Mockito.when(this.pautaRepository.findById(ID)).thenReturn(pautaOp);
		Mockito.when(this.cpfService.validarCpfAssociado(registroVotoRequestDto)).thenReturn(Optional.of(respostaRequisicaoCpfDto));
		
		RestAssured
			.given()
				.port(this.port)
				.body(registroVotoRequestDto)
				.contentType(ContentType.JSON)
			.when()
				.post("/votos/v1.0")
			.then()
				.log().all()
				.statusCode(HttpStatus.CREATED.value())
//				.body("id", Matchers.is(1));
				.body(containsString("registrado"));
	}

}
