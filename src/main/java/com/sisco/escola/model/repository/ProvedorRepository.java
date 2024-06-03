package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Provedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvedorRepository extends JpaRepository<Provedor, Long> {
    
    boolean existsByProvedorInternet(String provedorInternet);
    
    boolean existsByVelocidadeLink(String velocidadeLink);
    
}
