package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    /*buscar escola*/
    Escola buscar(String nome, String codigo);
    /*listar escolas*/
    List<Escola> listar(Escola escola);
}


