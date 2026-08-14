package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.LinkInternet;

@Repository
public interface LinkInternetRepository extends JpaRepository<LinkInternet, UUID> {

	List<LinkInternet> findByContratoInternet_Uuid(UUID contratoUuid);

	List<LinkInternet> findByContratoInternet_UuidAndIsAtivo(UUID contratoUuid, Boolean isAtivo);

}
