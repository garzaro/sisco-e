package com.sisco.escola.service.impl;

import com.sisco.escola.model.entity.Checkpoint;
import com.sisco.escola.model.repository.CheckpointRepository;
import com.sisco.escola.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckpointServiceImpl implements CheckpointService {
    
    @Autowired
    CheckpointRepository checkpointRepository;
    
    public CheckpointServiceImpl(CheckpointRepository checkpointRepository) {
        super();
        this.checkpointRepository = checkpointRepository;
    }
    
    @Override
    public Checkpoint metodoParaValidarCheckpointPeloNome(String nomeCheckpoint) {
        return null;
    }
    
    @Override
    public Checkpoint metodoParaValidarCheckpointPeloMac(String macAddress) {
        return null;
    }
    
    @Override
    public Checkpoint metodoParaValidarCheckpointPeloSerialNumber(String serialNumber) {
        return null;
    }
    
    @Override
    public Checkpoint metodoParaPersistirCheckpointNaBaseDeDados(Checkpoint nomeCkeckpoint) {
        return null;
    }
}
