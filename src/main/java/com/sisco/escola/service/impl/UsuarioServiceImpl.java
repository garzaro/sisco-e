package com.sisco.escola.service.impl;

import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
    
    UsuarioRepository usuarioRepository;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Usuario metodoParaValidarUsuario(String emailLogin, String senhalogin) {
        return null;
    }
    
    @Override
    public Usuario metodoParaPersistirUsuarioNaBaseDeDados(Usuario usuario) {
        return null;
    }
    
    @Override
    public void metodoParaValidarEmailDeLoginNaBaseDeDados(String emailLogin) {
        /*ver se existe*/
        boolean consultarSeEmailLoginExisteNaBaseDeDados = usuarioRepository.existsByEmailLogin(emailLogin);
        if (consultarSeEmailLoginExisteNaBaseDeDados){
            throw new RegraDeNegocioException("Um calango ou uma calanga ja usou esse email.");
        }
    
    }
}
