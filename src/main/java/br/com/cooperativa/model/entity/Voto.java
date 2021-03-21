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
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VOTO", uniqueConstraints={@UniqueConstraint(columnNames = {"ID_PAUTA", "CPF_ASSOCIADO"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Voto implements Serializable {

	private static final long serialVersionUID = -2673277897203328585L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_PAUTA", nullable = false)
	private Pauta pauta;
	
	@Column(name = "CPF_ASSOCIADO", nullable = false, length = 11)
	private String cpfAssociado;
	
	@Column(name = "BL_CONCORDA", nullable = false)
	private Boolean concorda;
	
	@Column(name = "DT_VOTO")
	private LocalDateTime dataVoto;

}
