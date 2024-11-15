package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {
    
    /*verificar se o usuario existe na base, validação*/
    Usuario autenticarUsuario(String email, String senha);
    
    /*salvar o usuario na base*/
    Usuario salvarUsuario(Usuario usuario);
    
    /*verifica o email na base de dados, unique */
    void validarEmailECpf(String email, String cpf);
    
    Optional<Usuario> obterUsuarioPorId(Long id);
    
    /*IMPLEMTAR SERVICO BUSCAR POR CPF*/
    
}
