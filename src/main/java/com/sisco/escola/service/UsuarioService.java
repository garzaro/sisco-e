package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvar(Usuario usuario);

    /**validar o email - ao salvar nao deve duplicar*/
    void validarEmail(String email);

    Optional<Usuario> obterUsuarioPorId(Long id);

    Optional<Usuario> pegarUsuarioPorCpf(String cpf);
}
