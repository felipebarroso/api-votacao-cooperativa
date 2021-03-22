package br.com.cooperativa.model.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cooperativa.model.entity.Voto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public @Data class RegistroVotoForm {

	@NotNull(message = "Pauta não informada")
	private Long pautaId;
	
	@NotBlank(message = "CPF do associado não informado")
	@Size(min = 11, max=11, message = "O CPF deve conter 11 dígitos")
	private String cpfAssociado;
	
	@NotNull(message = "Voto não informado")
	private Boolean concordaComPauta;
	
	public Voto converterDtoParaVoto() {
		return Voto.builder()
				.pautaId(this.pautaId)
				.cpfAssociado(this.cpfAssociado)
				.concordaComPauta(this.concordaComPauta)
				.dataVoto(LocalDateTime.now())
				.build();
	}

}
