package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;

public interface UsuarioService {
    
    /*verificar se o usuario existe na base*/
    Usuario metodoParaValidarUsuario(String emailLogin, String senhalogin);
    
    /*salvar o usuario na base*/
    Usuario metodoParaPersistirUsuarioNaBaseDeDados(Usuario usuario);
    
    /*email unique*/
    void metodoParaValidarEmailDeLoginNaBaseDeDados(String emailLogin);
    
    
    
}
