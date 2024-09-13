package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EscolaService {
    /*verificar se a escola existe na base*/
    List<Escola> buscarEscolaPorNome(Escola escola);
    
    /*verificar se existe o codigo na base*/
    Optional<Escola> buscarEscolaPorCodigo(Escola codigoEscola);
    
    /*salvar a escola na base*/
    Escola salvarEscola(Escola nomeEscola);
    
    /*verificar escola na base de dados, unique*/
    void validarEscolaNaBase(String nomeEscola);
    
    /*verificar cadastro na base de dados, unique*/
    void validarCodigo(String cadastroEscola);
}


