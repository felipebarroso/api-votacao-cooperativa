package br.com.cooperativa.model.dto;

import org.springframework.data.domain.Page;

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
public @Data class AssembleiaDto {

	private Long id;
	private String descricao;

	public AssembleiaDto(Assembleia assembleia) {
		this.id = assembleia.getId();
		this.descricao = assembleia.getDescricao();
	}
	
	public static Page<AssembleiaDto> converterParaDto(Page<Assembleia> assembleias) {
		return assembleias.map(AssembleiaDto::new);
	}

}
