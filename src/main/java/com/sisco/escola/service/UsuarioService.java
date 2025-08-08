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
         
    void validar(Usuario usuario);
    
    Optional<Usuario> obterUsuarioPorId(Long id);

	Optional<Usuario> pegarUsuarioPorCpf(String cpf);
    
    /*IMPLEMTAR SERVICO BUSCAR POR CPF*/
    
}
