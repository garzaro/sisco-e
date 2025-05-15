package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {
    
    /*verificar se o usuario existe na base, validação*/
    Usuario autenticar(String email, String senha);
    
    /*salvar o usuario na base*/
    Usuario salvar(Usuario usuario);
    
    /*atualiza o usuario*/
    Usuario atualizar(Usuario usuario);
    
    /*verifica o email e cpf na base de dados, unique 
    void validarUsuario(
    		String nomeCompleto,
    		String cpf,
    		String nomeUsuario,
    		String email,
    		String senha
    		);*/
    
    void validar(Usuario usuario);
    
    Optional<Usuario> obterUsuarioPorId(Long id);
    
    /*IMPLEMTAR SERVICO BUSCAR POR CPF*/
    
}
