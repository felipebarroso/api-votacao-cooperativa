package br.com.cooperativa.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAUTA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Pauta implements Serializable {

	private static final long serialVersionUID = 1364079322901338658L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false)
	private Long id;
	
	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "ID_ASSEMBLEIA", nullable = false)
	private Assembleia assembleia;
	
	@Column(name = "DT_INICIO_VOTACAO")
	private LocalDateTime dataInicioVotacao;
	
	@Column(name = "DT_FIM_VOTACAO")
	private LocalDateTime dataFimVotacao;
	
	@Column(name = "QTD_VOTOS_SIM")
	private Long quantidadeVotosSim;
	
	@Column(name = "QTD_VOTOS_NAO")
	private Long quantidadeVotosNao;
	
	
	@Transient
	private Integer quantidadeVotosPendentes;
	
	public void setQuantidadeVotosSim(Long quantidadeVotosSim) {
		this.quantidadeVotosSim = quantidadeVotosSim == null ? 0 : quantidadeVotosSim;
	}
	
	public void setQuantidadeVotosNao(Long quantidadeVotosNao) {
		this.quantidadeVotosNao = quantidadeVotosNao == null ? 0 : quantidadeVotosNao;
	}
	
	public boolean possuiVotosPendentes() {
		return this.quantidadeVotosPendentes == null || quantidadeVotosPendentes > 0;
	}
	
	public boolean votosNaoContabilizados() {
		return this.quantidadeVotosNao == null && this.quantidadeVotosSim == null;
	}
	
	public boolean sessaoEncerrada() {
		return this.dataFimVotacao != null && this.dataFimVotacao.isBefore(LocalDateTime.now());
	}
	
	public boolean podeContabilizarVotosDaSessao() {
		return sessaoEncerrada() && !possuiVotosPendentes() && votosNaoContabilizados();
	}
	
	public boolean aprovada() {
		return !votosNaoContabilizados() && this.quantidadeVotosSim > this.quantidadeVotosNao;
	}

}
