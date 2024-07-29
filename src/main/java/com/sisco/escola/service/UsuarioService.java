package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;

public interface UsuarioService {
    
    /*verificar se o usuario existe na base, validação*/
    Usuario validarLogin(String emailLogin, String senhalogin);
    
    /*salvar o usuario na base*/
    Usuario persistirUsuario(Usuario usuario);
    
    /*verifica o email na base de dados, unique */
    void validarEmailLogin(String emailLogin);
    
    
    
}
