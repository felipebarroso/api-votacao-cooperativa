package br.com.cooperativa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cooperativa.model.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	@Query("SELECT pauta "
			+ " FROM Pauta pauta "
			+ " WHERE pauta.dataFimVotacao IS NOT NULL "
			+ "		AND pauta.quantidadeVotosSim IS NULL "
			+ "		AND pauta.dataFimVotacao < :dataAtual")
	List<Pauta> pesquisarPautasFinalizadasParaEncerramento(@Param("dataAtual") LocalDateTime dataAtual);
	
}
