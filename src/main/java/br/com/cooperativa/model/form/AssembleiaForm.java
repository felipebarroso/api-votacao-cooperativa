package br.com.cooperativa.model.form;

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
public @Data class AssembleiaForm {

	@NotBlank(message = "A assembleia deve possuir uma descrição")
	@Size(max=200, message = "A descrição da assembleia pode conter no máximo 200 caracteres")
	private String descricao;

	public Assembleia converter() {
		return Assembleia.builder().descricao(this.descricao).build();
	}

}
