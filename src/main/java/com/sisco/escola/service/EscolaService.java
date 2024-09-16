package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EscolaService {
	
    /*salvar a escola na base*/
    Escola salvar(Escola escola);
    /*atualizar escola*/
    Escola atualizar(Escola escola);
    /*deletar escola*/
    void deletarEscola(Escola escola);
    /*buscar escola**/
    List<Escola> buscarEscola(Escola escola);
    /*verificar escola na base de dados, unique*/
    void validarEscola(String escola);
    /*verificar escola na base de dados, unique*/
    void validarCodigo(String escola);
}


