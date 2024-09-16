package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EscolaService {
    /*verificar escola na base de dados, unique*/
    boolean verificarExistenciaEscola(String nomeEscola);
    /*verificar escola na base de dados, unique*/
    boolean verificarExistenciaCodigo(String nomeEscola);
    /*salvar a escola na base*/
    Escola salvar(Escola escola);
    /*atualizar escola*/
    Escola atualizar(Escola escola);
    /*deletar escola*/
    void deletarEscola(Escola escola);
    /*buscar escola**/
    List<Object> buscarEscola(String escola);
    
}


