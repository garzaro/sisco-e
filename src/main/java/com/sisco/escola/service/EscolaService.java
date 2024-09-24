package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface EscolaService {
    /*recebe uma escola e salva na base*/
    Escola salvar(Escola escola);
    /*atualizar escola*/
    Escola atualizar(Escola escola);
    /*deletar uma escola*/
    void deletar(Escola escola);
    /*validar escola antes de salvar, unique*/
    void validarEscola(Escola escola);
    /*procura uma escola pelo id*/
    Optional <Escola> obterEscolaPorId(Long id);
    /*procura uma escola pelo nome*/
    Optional <Escola> buscarPorNome(String nome);
    /*procura uma escola pelo codigo*/
    Optional <Escola> buscarPorCodigo(String codigo);
}


