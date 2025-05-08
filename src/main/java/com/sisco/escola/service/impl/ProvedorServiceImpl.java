package com.sisco.escola.service.impl;

import com.sisco.escola.model.entity.Provedor;
import com.sisco.escola.model.repository.ProvedorRepository;
import com.sisco.escola.service.ProvedorService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProvedorServiceImpl implements ProvedorService {
    
    @Autowired
    ProvedorRepository provedorRepository;
    
    public ProvedorServiceImpl(ProvedorRepository provedorRepository) {
        super();
        this.provedorRepository = provedorRepository;
    }
    
    @Override
    public Provedor metodoParaValidarProvedorInternet(String provedorInternetValidado) {
        return null;
    }
    
    @Override
    public Provedor metodoParaPersistirProvedorInternetNaBaseDeDados(Provedor provedorInternetPersistido) {
        return null;
    }
    
    @Override
    public void metodoParaValidarProvedorInternetNaBaseDeDados(String provedorInternetUnique) {
    
    }
}
