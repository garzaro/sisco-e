package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    
    /*verificar se o usuario existe na base, validação*/
    Usuario autenticarUsuario(String email, String senha);
    
    /*salvar o usuario na base*/
    Usuario persistirUsuario(Usuario usuario);
    
    /*verifica o email na base de dados, unique */
    void validarEmailLogin(String email);
}
