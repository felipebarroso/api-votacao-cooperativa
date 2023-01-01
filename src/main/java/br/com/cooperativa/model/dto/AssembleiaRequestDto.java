package br.com.cooperativa.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.cooperativa.model.entity.Assembleia;
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
public @Data class AssembleiaRequestDto {

	@NotBlank(message = "A assembleia deve possuir uma descri��o")
	@Size(max=200, message = "A descri��o da assembleia pode conter no m�ximo 200 caracteres")
	private String descricao;

	public Assembleia converterDtoParaAssembleia() {
		return Assembleia.builder().descricao(this.descricao).build();
	}

}
