package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EscolaServiceImpl implements EscolaService {

	private EscolaRepository escolaRepository;

    public EscolaServiceImpl(EscolaRepository escolaRepository) {
		this.escolaRepository = escolaRepository;
	}

	/**
	 * @return
	 *************************************************************/
	@Override
	@Transactional
	public Escola salvar(Escola escola) {
		validarEscola(escola);
		return escolaRepository.save(escola);

	}

	@Override
	public Escola atualizar(Escola escola) {
		return null;
	}

	@Override
	public void deletarEscola(Escola escola) {

	}

	@Override
	public List<Object> buscarEscola(String escola) {
		return List.of();
	}

	public void validarEscola(Escola escola) {
		/*verificar se a escola ja existe na base, unique*/
		if (escola.getNomeEscola() == null || escola.getNomeEscola().trim().equals("")) {
			throw new ErroValidacaoException("Informar o nome da escola.");
		}
		if (escola.getCodigoEscola() == null || escola.getCodigoEscola().trim().equals("")) {
			throw new ErroValidacaoException("Informe o codigo da escola.");
		}

	}

	
	

	
    
    
    /*@Override
    public List<Escola> buscarEscolaPorNome(Escola nomeEscola) {
        Example example = Example.of(nomeEscola, ExampleMatcher
                .matching()
                /*ignora se o usuario digitou com caixa alta ou baixa
                .withIgnoreCase()
                /*contendo o que for passado na busca - CONTAINING
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return escolaRepository.findAll(example);
    }*/
    
    /*@Override
    public Optional<Escola> buscarEscolaPorCodigo(Escola codigoEscola) {
        Optional<Escola> codigo = escolaRepository.findByCodigoEscola(String.valueOf(codigoEscola));
        if (!codigo.isPresent()){
            throw new EscolaInexitenteException("Escola não encontrada com o codigo fornecido");
        }
        return Optional.ofNullable(codigoEscola);
    }*/
    
       
    /*@Override
    public void validarEscola(Escola nomeEscola) {
        /*ver se existe escola - unique
        boolean verificarEscola = escolaRepository.existsByNomeEscola(String.valueOf(nomeEscola));
        if (verificarEscola) {
            throw new RegraDeNegocioException("Já existe uma escola com esse nome.");
        }
    }*/
    
    /*@Override
    public void verificarExistenciaCodigo(Escola cadastroEscola) {
        /*ver se existe cadastro da escola - inep
        boolean verificarCadastroEscola = escolaRepository.existsByCodigoEscola(String.valueOf(cadastroEscola));
        if (verificarCadastroEscola) {
        throw new CodigoJaCadastradoException("Já existe uma escola com esse codigo");
        
        }
    }*/


}

            
            
            
