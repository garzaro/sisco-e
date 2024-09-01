package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@NoArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
   
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	/*login: validação, autenticação*/
	@Override
	public Usuario autenticarUsuario(String email, String senha) {
	    Optional<Usuario> validandoLogin = usuarioRepository.findByEmail(email);
	    /*verificar a existencia de usuario na base de dados*/
		if(!validandoLogin.isPresent()) {
			throw new ErroDeAutenticacao("Usuario não encontrado pelo email informado");
		}
		if (!validandoLogin.get().getSenha().equals(senha)){
			throw new ErroDeAutenticacao("Digite a senha correta");
		}
			return validandoLogin.get();
	}
	    
	@Override
	@Transactional
	public Usuario persistirUsuario(Usuario usuario) {
		/*service*/
		validarEmail(usuario.getEmail());
		validaCPF(usuario.getCadastroPessoaFisica());
		return usuarioRepository.save(usuario);
	}
	    
	@Override
	public void validarEmail(String email) {
		/*ver se existe email, unique*/
		boolean verificarSeOEmailExisteNaBaseDeDados = usuarioRepository.existsByEmail(email);
		if (verificarSeOEmailExisteNaBaseDeDados){
			throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
        }
    }
	
	@Override
	public void validaCPF(String cadastroPessoaFisica) {
		/*ver se existe cpf, unique*/
		boolean verificarSeCpfExisteNaBase = usuarioRepository.existsByCadastroPessoaFisica(cadastroPessoaFisica);
		if (verificarSeCpfExisteNaBase) {
			throw new RegraDeNegocioException("Já existe um usuario com esse CPF");
		}
	}
}
