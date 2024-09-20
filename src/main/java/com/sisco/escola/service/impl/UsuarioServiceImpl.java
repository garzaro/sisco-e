package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NoArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	UsuarioService usuarioService;
	
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
		if (!validandoLogin.isPresent()) {
			throw new ErroDeAutenticacao("Usuario não encontrado pelo email informado");
		}
		if (!validandoLogin.get().getSenha().equals(senha)) {
			throw new ErroDeAutenticacao("Senha incorreta");
		}
		return validandoLogin.get();
	}
	
	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		/*service*/
		validarEmailECpf(usuario.getEmail(), usuario.getCadastroPessoaFisica());
		/*validarCPF(usuario.getCadastroPessoaFisica());*/
		/*salvar o usuario*/
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public void validarEmailECpf(String email, String cpf) {
		/*ver se existe email e cpf na base, unique*/
		if (StringUtils.isBlank(email)) {
			throw new ErroValidacaoException("O email é obrigatório.");
		}
		if (StringUtils.isBlank(cpf)) {
			throw new ErroValidacaoException("O cpf é obrigatório.");
		}
		
		boolean verificarSeEmailExisteNaBaseDeDados = usuarioRepository.existsByEmail(email);
		boolean verificarSeOCpfExisteNaBaseDeDados = usuarioRepository.existsByCadastroPessoaFisica(cpf);
		
		if (verificarSeEmailExisteNaBaseDeDados) {
			throw new ErroValidacaoException("Ja existe um usuario com esse email.");
		}
		if (verificarSeOCpfExisteNaBaseDeDados) {
			throw new ErroValidacaoException("Ja existe um usuario com esse CPF");
		}
	}
	
	@Override
	public Optional<Usuario> obterUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}
	
}

	
