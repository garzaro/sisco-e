package com.sisco.escola.service;

import com.sisco.escola.model.entity.Escola;

public interface EscolaService {
    
    /*verificar se a escola existe na base, inep*/
    Escola verificarEscolaJaCadastrada(String nomeEscola, String cadastroEscola);/*inep*/
    
    /*salvar a escola na base*/
    Escola persistirEscola(Escola nomeEscola);
    
    /*verificar escola na base de dados, unique*/
    void validarEscola(String inep);
}

