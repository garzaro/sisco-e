package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.enums.TipoEscola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, UUID> {

	boolean existsByNomeEscola(String nomeEscola);

	boolean existsByCodigoEscola(String codigoEscola);

	Optional<Escola> findByCodigoEscola(String codigoEscola);
	
	List<Escola> findTop10ByNomeEscolaContainingIgnoreCase(String parteNomeEscola);

	Page<Escola> findByMunicipio(String municipio, Pageable pageable);

	Page<Escola> findByTipoEscola(TipoEscola tipoEscola, Pageable pageable);

	Page<Escola> findByMunicipioAndEstado(String municipio, String estado, Pageable pageable);

	Page<Escola> findByEstado(String estado, Pageable pageable);

}
