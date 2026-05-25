package com.sisco.escola.service;

import com.sisco.escola.model.entity.Provedor;

public interface ProvedorService {
    
    /*verificar se o provedor existe na base*/
    Provedor metodoParaValidarProvedorInternet(String provedorInternetValidado);
    
    /*salvar o provedor na base*/
    Provedor metodoParaPersistirProvedorInternetNaBaseDeDados(Provedor provedorInternetPersistido);
    
    /*dever ser unique*/
    void metodoParaValidarProvedorInternetNaBaseDeDados(String provedorInternetUnique);
}
