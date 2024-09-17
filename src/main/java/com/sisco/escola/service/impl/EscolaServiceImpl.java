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
		return escolaRepository.save(escola);
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
		boolean ve = escolaRepository.existsByNome(escola.getNome());
		if (ve) {
			throw new ErroValidacaoException("Escola ja cadastrada.");
		}
		boolean vc = escolaRepository.existsByCodigo(escola.getCodigo());
		if (vc) {
			throw new ErroValidacaoException("Código já cadastrado");
		}
    }
}

            
            
            
