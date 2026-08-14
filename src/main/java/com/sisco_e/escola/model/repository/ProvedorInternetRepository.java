package com.sisco_e.escola.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.ProvedorInternet;

@Repository
public interface ProvedorInternetRepository extends JpaRepository<ProvedorInternet, UUID> {

}
