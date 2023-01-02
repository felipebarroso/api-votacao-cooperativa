package br.com.cooperativa.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cooperativa.model.dto.QuantidadeVotosDto;
import br.com.cooperativa.model.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
	
	@Query("SELECT new br.com.cooperativa.model.dto.QuantidadeVotosDto( "
			+ " 	SUM(CASE voto.concordaComPauta WHEN true THEN 1 ELSE 0 END), "
			+ " 	SUM(CASE voto.concordaComPauta WHEN true THEN 0 ELSE 1 END) "
			+ " ) "
			+ " FROM Voto voto "
			+ " WHERE voto.dataVoto >= :dataInicio "
			+ "		AND voto.dataVoto <= :dataFim "
			+ " 	AND voto.pautaId = :pautaId")
	QuantidadeVotosDto pesquisarQuantidadeDeVotosPorPautaFinalizada(@Param("pautaId") Long pautaId, 
			@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
	
	@Query("SELECT voto "
			+ " FROM Voto voto "
			+ " WHERE voto.dataVoto >= :dataInicio "
			+ "		AND voto.dataVoto <= :dataFim "
			+ " 	AND voto.pautaId = :pautaId "
			+ " 	AND voto.cpfAssociado = :cpfAssociado")
	Optional<Voto> pesquisarVotoPorAssociadoEPauta(@Param("pautaId") Long pautaId, @Param("dataInicio") LocalDateTime dataInicio, 
			@Param("dataFim") LocalDateTime dataFim, @Param("cpfAssociado") String cpfAssociado);
	
}
