package br.com.cooperativa.model.form;

import javax.validation.constraints.NotNull;

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
public @Data class InicioPautaForm {

	@NotNull(message = "Pauta não informada")
	private Long pautaId;
	
	private Long duracaoSessaoEmMinutos;

}
