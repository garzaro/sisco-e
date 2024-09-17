package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EscolaServiceImpl implements EscolaService {
	
	@Autowired
	private EscolaRepository escolaRepository;
	/**
	 *
	 *************************************************************/
	@Override
	@Transactional
	public Escola salvar(Escola escola) {
		validarEscola(escola);
		verificarEscola(escola.getNome());
		verificarCodigo(escola.getCodigo());
		return escolaRepository.save(escola);
	}
	
	@Override
	public void verificarCodigo(String codigo) {
		/*ver se existe o codigo, unique*/
		boolean validarCodigo = escolaRepository.existsByCodigo(codigo);
		if (validarCodigo) {
			throw new ErroValidacaoException("Codigo já cadastrado");
		}
	
	}
	
	public void verificarEscola(String escola) {
		/*ver se existe a escola, unique*/
		boolean validarEscola = escolaRepository.existsByNome(escola);
		if (validarEscola) {
			throw new ErroValidacaoException("Escola já cadastrada.");
		}
	}
	
	@Override
	public void validarEscola(Escola escola) {
		/*preencher campos*/
		if (escola.getNome() == null || escola.getNome().trim().equals("")) {
			throw new ErroValidacaoException("Informar o nome da escola.");
		}
		if (escola.getCodigo() == null || escola.getCodigo().trim().equals("")) {
			throw new ErroValidacaoException("Informe o codigo da escola.");
		}
	}
}

            
            
            
