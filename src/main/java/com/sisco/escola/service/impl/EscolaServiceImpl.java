package com.sisco.escola.service.impl;

import com.sisco.escola.exception.*;
import com.sisco.escola.exception.ErroValidacaoEscola;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;

import java.util.Optional;

public class EscolaServiceImpl implements EscolaService {
    
    EscolaRepository escolaRepository;
    
    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }
    
    @Override
    public Escola verificarEscolaCadastrada(String nomeEscola, String cadastroEscola) {
        return escolaRepository.existsByNomeEscolaOrCadastroEscola()
    }
    
    
    @Override
    public Escola persistirEscola(Escola cadastroEscola) {
    	/*service*/
    	validarEscola(cadastroEscola.getNomeEscola());
        return null;
    }
}


    
    @Override
    public Usuario persistirUsuario(Usuario usuarioLogin) {
        /*service*/
        validarEmailLogin(usuarioLogin.getEmailLogin());
        return usuarioRepository.save(usuarioLogin);
    }
    
    @Override
    public void validarEmailLogin(String emailLogin) {
        /*ver se existe email*/
        boolean verificarSeOEmailLoginExisteNaBaseDeDados = usuarioRepository.existsByEmailLogin(emailLogin);
        if (verificarSeOEmailLoginExisteNaBaseDeDados){
            throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
    }
}

	
	@Override
	public void validarEscola(String nomeEscola) {
		// TODO Auto-generated method stub
		
	}
	
	 @Override
	    public Usuario persistirUsuario(Usuario usuarioLogin) {
	        /*service*/
	        validarEmailLogin(usuarioLogin.getEmailLogin());
	        return usuarioRepository.save(usuarioLogin);
	    }
	    
	    @Override
	    public void validarEmailLogin(String emailLogin) {
	        /*ver se existe email*/
	        boolean verificarSeOEmailLoginExisteNaBaseDeDados = usuarioRepository.existsByEmailLogin(emailLogin);
	        if (verificarSeOEmailLoginExisteNaBaseDeDados){
	            throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
     }
            
            
            
            
            public void validarEmailNaBaseDedados(String email) {
                /*ver se o email existe*/
                boolean verificarSeOEmailExisteNaBaseDeDados = usuarioRepository.existsByEmail(email);
                if (verificarSeOEmailExisteNaBaseDeDados){
                    throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
                }
