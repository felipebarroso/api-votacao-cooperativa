package br.com.cooperativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cooperativa.model.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
}
