package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.ContratoInternet;
import com.sisco_e.escola.model.enums.StatusContrato;

@Repository
public interface ContratoInternetRepository extends JpaRepository<ContratoInternet, UUID> {

	@Query("""
			SELECT c 
			FROM ContratoInternet c
			JOIN FETCH c.escola
			JOIN FETCH c.provedor
			WHERE c.status = com.sisco_e.escola.model.enums.StatusContrato.ATIVO
			""")
	List<ContratoInternet> findContratosAtivosComEscolaEProvedor();

	List<ContratoInternet> findByEscola_UuidAndStatus(UUID escolaUuid, StatusContrato status);

}
