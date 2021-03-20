package br.com.cooperativa.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ASSEMBLEIA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Assembleia implements Serializable {

	private static final long serialVersionUID = 3513859440457180417L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false)
	private Long id;
	
	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;

}
