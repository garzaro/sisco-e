package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.enums.TipoEscola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, UUID> {

	boolean existsByNomeEscola(String nomeEscola);

	boolean existsByCodigoEscola(String codigoEscola);

	Optional<Escola> findByCodigoEscola(String codigoEscola);
	
	Optional<Escola> findByNomeEscola(String nomeEscola);

	List<Escola> findByMunicipio(String municipio);

	List<Escola> findByEstado(String estado);	

	List<Escola> findByTipoEscola(TipoEscola tipoEscola);

	List<Escola> findByNomeEscolaContainingIgnoreCase(String parteNomeEscola);

	List<Escola> findByMunicipioAndEstado(String municipio, String estado);

}
