package br.com.cooperativa.model.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

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
public @Data class PautaDto {

	private Long id;
	private String descricao;
	private String assembleia;
	private LocalDateTime dataInicioVotacao;
	private LocalDateTime dataFimVotacao;
	private Long quantidadeVotosSim;
	private Long quantidadeVotosNao;
	private Boolean encerrada;
	private Boolean aprovada;

	public PautaDto(Pauta pauta) {
		this.id = pauta.getId();
		this.descricao = pauta.getDescricao();
		this.assembleia = pauta.getAssembleia().getDescricao();
		this.dataInicioVotacao = pauta.getDataInicioVotacao();
		this.dataFimVotacao = pauta.getDataFimVotacao();
		this.quantidadeVotosSim = pauta.getQuantidadeVotosSim();
		this.quantidadeVotosNao = pauta.getQuantidadeVotosNao();
		this.encerrada = this.dataFimVotacao.isBefore(LocalDateTime.now());
		this.aprovada = this.quantidadeVotosSim > this.quantidadeVotosNao;
	}
	
	public static Page<PautaDto> converter(Page<Pauta> pautas) {
		return pautas.map(PautaDto::new);
	}

}
