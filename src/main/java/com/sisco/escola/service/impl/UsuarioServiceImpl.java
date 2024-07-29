package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
   
public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
    super();
    this.usuarioRepository = usuarioRepository;
}
/*login: validação, autenticação*/
@Override
public Usuario validarLogin(String emailLogin, String senhalogin) {
    Optional<Usuario> validandoLogin = usuarioRepository.findByEmailLogin(emailLogin);
    /*verificar a existencia de usuario na base de dados*/
        if(!validandoLogin.isPresent()){
            throw new ErroDeAutenticacao("Usuario não encontrado");
        }
        if (!validandoLogin.get().getSenhaLogin().equals(senhalogin)){
            throw new ErroDeAutenticacao("Digite a senha correta");
        }
        return validandoLogin.get();
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
}
