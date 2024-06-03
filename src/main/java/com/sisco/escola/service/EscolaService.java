package com.sisco.escola.service;

import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;

public interface EscolaService {
    
    /*verificar se a escola existe na base*/
    Usuario metodoParaValidarEscola(String nomeEscola, String cidadeEscola);
    
    /*salvar a escola na base*/
    Usuario metodoParaPersistirEscolaNaBaseDeDados(Escola nomeEscola);
    
}
