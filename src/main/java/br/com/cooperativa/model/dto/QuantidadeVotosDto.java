package br.com.cooperativa.model.dto;

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
public @Data class QuantidadeVotosDto {

	private Long quantidadeVotosSim;
	private Long quantidadeVotosNao;

}
