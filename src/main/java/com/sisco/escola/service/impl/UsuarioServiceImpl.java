package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@NoArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    UsuarioRepository usuarioRepository;
   
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
	    this.usuarioRepository = usuarioRepository;
	}
	/*login: validação, autenticação*/
	@Override
	public Usuario autenticarUsuario(String email, String senha) {
	    Optional<Usuario> validandoLogin = usuarioRepository.findByEmail(email);
	    /*verificar a existencia de usuario na base de dados*/
	        if(!validandoLogin.isPresent()){
	            throw new ErroDeAutenticacao("Usuario não encontrado");
	        }
	        if (!validandoLogin.get().getSenha().equals(senha)){
	            throw new ErroDeAutenticacao("Digite a senha correta");
	        }
	        return validandoLogin.get();
	    }
	    
	    @Override
	    public Usuario persistirUsuario(Usuario usuario) {
	        /*service*/
	        validarEmail(usuario.getEmail());
	        return usuarioRepository.save(usuario);
	    }
	    
	    @Override
	    public void validarEmail(String email) {
	        /*ver se existe email*/
	        boolean verificarSeOEmailExisteNaBaseDeDados = usuarioRepository.existsByEmail(email);
	        if (verificarSeOEmailExisteNaBaseDeDados){
	            throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
        }
    }
}
