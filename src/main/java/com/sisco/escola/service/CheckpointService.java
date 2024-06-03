package com.sisco.escola.service;

import com.sisco.escola.model.entity.Checkpoint;


public interface CheckpointService {
    
    /*verificar se o checkpoint existe na base*/
    Checkpoint metodoParaValidarCheckpointPeloNome(String nomeCheckpoint);
    
    /*verificar se o checkpoint existe na base*/
    Checkpoint metodoParaValidarCheckpointPeloMac(String macAddress);
    
    /*verificar se o checkpoint existe na base*/
    Checkpoint metodoParaValidarCheckpointPeloSerialNumber(String serialNumber);
    
    /*salvar o checkpoint na base*/
    Checkpoint metodoParaPersistirCheckpointNaBaseDeDados(Checkpoint nomeCkeckpoint);
    
}
