package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

@Service
public interface EscolaService {
    /*verificar se a escola existe na base, inep*/
    Escola buscarEscolaPorNomeOuCadastro(String nomeEscola, Integer cadastroEscola);/*inep*/
    
    /*salvar a escola na base*/
    Escola salvarEscola(Escola nomeEscola);
    
    /*verificar escola na base de dados, unique*/
    void validarEscola(String nomeEscola);
}

