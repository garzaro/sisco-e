package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@NoArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	/* lista de emails permitidos */
	private static final List<String> dominiosEmailPermitidos = List.of(
			"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "edu.br", "org", "gov.br");

	/* login: validação, autenticação */
	@Override
	public Usuario autenticarUsuario(String email, String senha) {

		Optional<Usuario> validandoLogin = usuarioRepository.findByEmail(email);
		/* verificar a existencia de usuario na base de dados */
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
		/* ver se o email e cpf ja existem na base de dados */
		validarUsuario(usuario);
		/* salvar o usuario */
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public Usuario atualizarUsuario(Usuario usuario) {
		Objects.requireNonNull(usuario.getId());
		validarUsuario(usuario);
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarUsuario(Usuario usuario) {
		
		/* preencher campos */
		
		if(usuario.getNomeCompleto() == null || usuario.getNomeCompleto().trim().equals("")) {
			throw new ErroValidacaoException("O nome completo é obrigatório.");			
		}
		
		if(usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().equals("")) {
			throw new ErroValidacaoException("O nome de usuário é obrigatório.");			
		}
		
		if (usuario.getEmail() == null || usuario.getEmail().trim().equals("")) {
			throw new ErroValidacaoException("O email é obrigatório.");
		}

		if (!Pattern.matches("^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$", usuario.getEmail())) {
			throw new ErroValidacaoException("O email deve seguir o padrao email@seudominio.com (br).");
		}		
		
		String emailPermitido = usuario.getEmail().substring(usuario.getEmail().lastIndexOf("@") + 1);
		if (!dominiosEmailPermitidos.contains(emailPermitido)) { /*garante que o dominio extraido esteja na lista*/
			throw new ErroValidacaoException("O email permitido deve constar da lista a seguir: " + dominiosEmailPermitidos);
			
		}		

		if (usuario.getCpf() == null || usuario.getCpf().trim().equals("")) {
			throw new ErroValidacaoException("O cpf é obrigatório.");
		}

		if (!Pattern.matches("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", usuario.getCpf())) {
			throw new ErroValidacaoException("O CPF deve seguir o padrao 000.000.000-00");
		}
		
		if (usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
			throw new ErroValidacaoException("informe a senha.");			
		}
		
		/* ver se existe email e cpf na base, unique */
		boolean verificarSeEmailExiste = usuarioRepository.existsByEmail(usuario.getEmail());
		boolean verificarSeOCpfExiste = usuarioRepository.existsByCpf(usuario.getCpf());

		if (verificarSeEmailExiste) {
			throw new ErroValidacaoException("Ja existe um usuario com esse email.");
		}
		if (verificarSeOCpfExiste) {
			throw new ErroValidacaoException("Ja existe um usuario com esse CPF");
		}
	}

	@Override
	public Optional<Usuario> obterUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}
}
