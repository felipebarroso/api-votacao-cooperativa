package br.com.cooperativa.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cooperativa.model.entity.Assembleia;
import br.com.cooperativa.model.entity.Pauta;
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
public @Data class PautaRequestDto {

	@NotBlank(message = "A pauta deve possuir uma descrição")
	@Size(max=200, message = "A descrição da pauta pode conter no máximo 200 caracteres")
	private String descricao;
	
	@NotNull(message = "A pauta deve pertencer a uma assembleia")
	private Long assembleiaId;

	public Pauta converterDtoParaPauta(Assembleia assembleia) {
		return Pauta.builder().descricao(this.descricao).assembleia(assembleia).build();
	}

}
