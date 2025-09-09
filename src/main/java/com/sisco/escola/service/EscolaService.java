package com.sisco.escola.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisco.escola.model.entity.Escola;

@Service
public interface EscolaService {
    /**quantidade de escolas cadastradas*/
    long totalEscola();
	/**recebe uma escola e salva na base*/
    Escola salvarEscola(Escola escola);
	/**atualizar escola*/
    Escola atualizarEscola(Escola escola);
    /**deletar uma escola*/
    void deletarEscola(Escola escola);
    /**listar escolas*/
    List<Escola> listarEscolas(Escola escolafiltro);
    /**validar escola antes de salvar, unique*/
    void validarEscola(Escola escola);
    /**procura uma escola pelo id*/
    Optional<Escola> obterEscolaPorId(Long id);
    /**buscar escola*/
    Optional<Escola> buscarEscolaPorNome(Escola nomeEscola);
    /**buscar escola por codigo*/
    Optional<Escola> buscarEscolaPorCodigo(String codigo);
    
    
}


