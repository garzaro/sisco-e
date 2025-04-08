package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    
    boolean existsByNomeCheckpoint(String nomeCheckpoint);
    
    boolean existsByMacAddress(String macAddress);
    
    boolean existsBySerialNumber(String serialNumber);
    
}
