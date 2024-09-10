package com.sisco.escola.service.impl;

import com.sisco.escola.exception.*;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import lombok.NoArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class EscolaServiceImpl implements EscolaService {
    
    EscolaRepository escolaRepository;
    
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }
    
    @Override
    public Escola buscarEscolaPorNomeOuCadastro(String nomeEscola, Integer cadastroEscola) { /*ver se a escola ja esta cadastrada*/
        Optional<Escola> escolaCadastro = escolaRepository.findByNomeEscolaOrCadastroEscola(nomeEscola, cadastroEscola);
        if (!escolaCadastro.isPresent()) {
            throw new CodigoInexistenteException("Codigo não encontrado.");
        }
        if (nomeEscola.isEmpty()) {
        	throw new EscolaInexitenteException("Escola não encontrada.");
			
		}
        
        return escolaCadastro.get();
    }
    
    @Override
    public Escola salvarEscola(Escola nomeEscola) {
        /*Escola escolaExitente = escolaRepository.findByNomeEscola(nomeEscola.getNomeEscola());
        if (escolaExitente != null) {
            /*excessao personalizada
            throw new RegraDeNegocioException("Esta escola já está cadastrada");
        }*/
        /*service*/
        validarEscola(nomeEscola.getNomeEscola());
        return escolaRepository.save(nomeEscola);
    }
    
    @Override
    public void validarEscola(String nomeEscola) {
        /*ver se existe escola*/
        boolean verificarEscola = escolaRepository.existsByNomeEscola(nomeEscola);
        if (verificarEscola) {
            throw new RegraDeNegocioException("Já existe uma escola com esse nome.");
        }
    }
}

	

            
            
            
