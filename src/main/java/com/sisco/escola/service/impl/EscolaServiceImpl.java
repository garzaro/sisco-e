package com.sisco.escola.service.impl;

import com.sisco.escola.exception.*;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class EscolaServiceImpl implements EscolaService {
    @Autowired
    EscolaRepository escolaRepository;
    /*@Autowired
    EscolaService escolaService;*/
    
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }
    
    @Override
    public List<Escola> buscarEscolaPorNome(Escola nomeEscola) {
        Example example = Example.of(nomeEscola, ExampleMatcher
                .matching()
                /*ignora se o usuario digitou com caixa alta ou baixa*/
                .withIgnoreCase()
                /*contendo o que for passado na busca - CONTAINING*/
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return escolaRepository.findAll(example);
    }
    
    @Override
    public Optional<Escola> buscarEscolaPorCodigo(Escola codigoEscola) {
        Optional<Escola> codigo = escolaRepository.findByCodigoEscola(String.valueOf(codigoEscola));
        if (!codigo.isPresent()){
            throw new EscolaInexitenteException("Escola não encontrada com o codigo fornecido");
        }
        return Optional.ofNullable(codigoEscola);
    }
    
    @Override
    @Transactional
    public Escola salvarEscola(Escola nomeEscola) {
        /*service*/
        validarEscola(nomeEscola);
        validarCodigo(nomeEscola);
        /*salvar a escola*/
        return escolaRepository.save(nomeEscola);
    }
    
    @Override
    public void validarEscola(Escola nomeEscola) {
        /*ver se existe escola - unique*/
        boolean verificarEscola = escolaRepository.existsByNomeEscola(String.valueOf(nomeEscola));
        if (verificarEscola) {
            throw new RegraDeNegocioException("Já existe uma escola com esse nome.");
        }
    }
    
    /*@Override*/
    public void validarCodigo(Escola cadastroEscola) {
        /*ver se existe cadastro da escola - inep*/
        boolean verificarCadastroEscola = escolaRepository.existsByCodigoEscola(String.valueOf(cadastroEscola));
        if (verificarCadastroEscola) {
        throw new CodigoJaCadastradoException("Já existe uma escola com esse codigo");
        
        }
    }
}

            
            
            
