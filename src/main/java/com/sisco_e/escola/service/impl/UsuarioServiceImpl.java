package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.UsuarioDTO;
import com.sisco_e.escola.exception.EmailAlreadyExistsException;
import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.UsuarioMapper;
import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.model.repository.UsuarioRepository;
import com.sisco_e.escola.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder; // Injetado via construtor

	@Override
	public UsuarioDTO autenticar(String email, String senha) {
		return null;
	}

	@Override
	@Transactional
	public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDto) {
		/** se existe **/
		validarEmail(usuarioDto.getEmail());
		validarCpf(usuarioDto.getCpf());
		/**Codifica antes de mapear para a entidade**/
		usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		/** agora que está convertido... **/
		Usuario entity = usuarioMapper.DtoToEntity(usuarioDto);
		entity.setIsAtivo(true);
		/** ...chamo o repository para salvar o dto convertido para entidade **/
		Usuario salvarUsuario = usuarioRepository.save(entity);
		return usuarioMapper.entityToDto(salvarUsuario);
	}

	@Override
	public void validarUsuario(UsuarioDTO usuarioDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarEmail(String email) {
		boolean existeEmail = usuarioRepository.existsByEmail(email);
		if (existeEmail) {
			throw new EmailAlreadyExistsException("Email já cadastrado.");
		}
	}
	
	@Override
	public void validarCpf(String cpf) {
		boolean existeCpf = usuarioRepository.existsByCpf(cpf);
		if (existeCpf) {
			throw new RegraNegocioException("{dados.duplicados.nao.permitido}");
		}
	}

	@Override
	public List<UsuarioDTO> buscarTodosOsUsuariosCadastrados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UsuarioDTO> buscarUsuarioPorId(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public UsuarioDTO atualizarUsuario(UsuarioDTO usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UsuarioDTO> buscarUsuarioPorEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<UsuarioDTO> buscarUsuarioPorNome(String nomeCompleto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<UsuarioDTO> buscarUsuarioPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<UsuarioDTO> buscarUsuarioPorParteDoNome(String parteDoNome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletarUsuario(UUID id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<UsuarioDTO> obterUsuarioPorId(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<UsuarioDTO> obterUsuarioPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public UsuarioDTO atualizarPerfilUsuario(UsuarioDTO usuario) {
		// TODO Auto-generated method stub
		return null;
	}
}
