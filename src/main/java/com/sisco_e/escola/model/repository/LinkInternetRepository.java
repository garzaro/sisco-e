package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.LinkInternet;

@Repository
public interface LinkInternetRepository extends JpaRepository<LinkInternet, UUID> {

	List<LinkInternet> findByContratoInternet_Uuid(UUID contratoUuid);

	List<LinkInternet> findByContratoInternet_UuidAndIsAtivo(
		UUID contratoUuid,
		 Boolean isAtivo
		);

// @EntityGraph(attributePaths = {
//     "contratoInternet",
//     "contratoInternet.provedor"
// })
// @EntityGraph(attributePaths = {"provedor", "escola"})
// List<LinkInternet> findByIpOrDns(@Param("termo") String termo);
//mesma coisa so que mais limpa

@Query("SELECT DISTINCT l FROM LinkInternet l " +
		"LEFT JOIN FETCH l.provedorInternet " +
		"LEFT JOIN FETCH l.escola " +
		"WHERE l.ipPublico = :termo " +
		"OR l.dnsPrimario = :termo " +
		"OR l.dnsSecundario = :termo")
List<LinkInternet> findByIpPublicoOrDns(@Param("termo") String termo);

}
