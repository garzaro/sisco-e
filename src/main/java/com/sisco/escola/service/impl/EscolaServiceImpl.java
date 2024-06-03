package com.sisco.escola.service.impl;

import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;
import org.springframework.beans.factory.annotation.Autowired;

public class EscolaServiceImpl implements EscolaService {
    
    @Autowired
    EscolaRepository escolaRepository;
    
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        super();
        this.escolaRepository = escolaRepository;
    }
    
    @Override
    public Usuario metodoParaValidarEscola(String nomeEscola, String cidadeEscola) {
        return null;
    }
    
    @Override
    public Usuario metodoParaPersistirEscolaNaBaseDeDados(Escola nomeEscola) {
        return null;
    }
}
