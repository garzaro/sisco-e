package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.EscolaNotFoundException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EscolaServiceImpl implements EscolaService {
    
    private EscolaRepository escolaRepository;
    
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }
    
    @Override
    @Transactional
    public Escola salvarEscola(Escola escola) {
        validarEscola(escola);
        return escolaRepository.save(escola);
    }
    
    @Override
    @Transactional
    public Escola atualizarEscola(Escola escola) {
        Objects.requireNonNull(escola.getId());
        validarEscola(escola);
        return escolaRepository.save(escola);
    }
    
    @Override
    public void deletarEscola(Escola escola) {
        /* so deleta se existir uma escola salva */
        Objects.requireNonNull(escola.getId());
        escolaRepository.delete(escola);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Escola> buscarEscolaPorNome(Escola nomeEscola) {
        
        Optional<Escola> resultadoDaBusca = escolaRepository.findByNome(nomeEscola);
        if (!resultadoDaBusca.isPresent()) {
            throw new EscolaNotFoundException("O nome de escola fornecido não foi encontrado");
        }
        return resultadoDaBusca;
    }
    
    @Override
    public Optional<Escola> buscarEscolaPorCodigo(String codigo) {
        
        /* buscar diretamente no repositorio, ignorando cases */
        Optional<Escola> codigoEscola = escolaRepository.findByCodigo(codigo);
        if (!codigoEscola.isPresent()) {
            throw new EscolaNotFoundException("Não foi encontrado escola com o código fornecido");
        }
        return codigoEscola;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Escola> listarEscolas(Escola escolaFiltro) {
        
        Example example = Example.of(escolaFiltro,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return escolaRepository.findAll(example);
    }
    
    @Override
    public Optional<Escola> obterEscolaPorId(Long id) {
        return escolaRepository.findById(id);
    }
    
    @Override
    public void validarEscola(Escola escola) {
        /* preencher campos */
        if (escola.getNome() == null || escola.getNome().trim().equals("")) {
            throw new EscolaNotFoundException("Informar um nome de escola.");
        }
        if (escola.getCodigo() == null || escola.getCodigo().trim().equals("")) {
            throw new EscolaNotFoundException("Informar o codigo da escola.");
        }
        /*escola ja existe*/
        boolean ve = escolaRepository.existsByNome(escola.getNome());
        /*codigo ja atribuido*/
        boolean vc = escolaRepository.existsByCodigo(escola.getCodigo());
        /*testar*/
        if (ve){
            throw new EscolaNotFoundException("Já existe uma escola cadastrada com esse nome.");
        }
        if (vc){
            throw new EscolaNotFoundException("O código informado pertence a uma escola já cadastrada.");
        }
        
        
        
        
    }
    
}
