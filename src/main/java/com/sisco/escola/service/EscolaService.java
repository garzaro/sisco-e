package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EscolaService {
    /*verificar escola na base de dados, unique*/
    void validarEscola(Escola nomeEscola);
    /*salvar a escola na base*/
    void salvar(Escola escola);
    /*atualizar escola*/
    Escola atualizar(Escola escola);
    /*deletar escola*/
    void deletarEscola(Escola escola);
    /*buscar escola**/
    List<Object> buscarEscola(String escola);
    
}


