package br.com.cooperativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cooperativa.model.entity.Assembleia;

@Repository
public interface AssembleiaRepository extends JpaRepository<Assembleia, Long> {
	
}
