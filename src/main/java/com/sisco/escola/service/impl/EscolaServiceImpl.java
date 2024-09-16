package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaServiceImpl implements EscolaService {
	@Autowired
    EscolaRepository escolaRepository;
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
		super();
		this.escolaRepository = escolaRepository;
	}
/***************************************************************/
public Escola salvar(Escola escola) {
	if (escolaRepository.findByNomeEscola(escola.getNomeEscola()).isPresent() ||
			escolaRepository.findByCodigoEscola(escola.getCodigoEscola()).isPresent()) {
		throw new IllegalArgumentException("Nome ou código já existente.");
	}
	Escola escola1 = new Escola();
	escola1.setNomeEscola(escola1.getNomeEscola());
	escola1.setCodigoEscola(escola1.getCodigoEscola());
	return escolaRepository.save(escola1);
}


















/*@Override
	public Escola salvar(Escola escola) {
		/*validar antes de salvar
		verificarExistenciaEscola(escola.getNomeEscola());
		verificarExistenciaCodigo(escola.getCodigoEscola());
		return escolaRepository.save(escola);
	}
	
	@Override
	public Escola atualizar(Escola escola) {
		return null;
	}
	
	@Override
	public List<Object> buscarEscola(String escola) {
		return List.of();
	}
	
	@Override
	public void deletarEscola(Escola escola) {
	
	}*/
	
	@Override
	public boolean verificarExistenciaEscola(String escola) {
		/*verificar se a escola ja existe na base, unique*/
		boolean validandoEscola = escolaRepository.existsByNomeEscola(escola);
		if (validandoEscola){
			throw new ErroValidacaoException("Escola já cadastrada.");
		}
		return validandoEscola;
    }
	
	@Override
	public boolean verificarExistenciaCodigo(String codigo) {
		/*verificar se o codigo ja existe na base, unique*/
		boolean validandoCodigo = escolaRepository.existsByCodigoEscola(codigo);
		if (validandoCodigo){
			throw new ErroValidacaoException("Codigo já cadastrado.");
		}
		return validandoCodigo;
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

	/*@Override
	public void validarEscola(String nomeEscola) {
		// TODO Auto-generated method stub
		
	}*/
}

            
            
            
