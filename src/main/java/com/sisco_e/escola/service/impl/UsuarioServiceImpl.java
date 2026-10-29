package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.LoginRequestDto;
import com.sisco_e.escola.api.dto.request.UsuarioRequestDTO;
import com.sisco_e.escola.api.dto.response.LoginResponseDTO;
import com.sisco_e.escola.api.dto.response.UsuarioResponseDTO;
import com.sisco_e.escola.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.exception.CpfAlreadyExistsException;
import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.UsuarioMapper;
import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.model.repository.UsuarioRepository;
import com.sisco_e.escola.service.UsuarioService;

import jakarta.transaction.Transactional;

/**
 * TODO-list
 * [x] Autenticar usuario
 * [x] Salvar usuario
 * [] Validar usuario
 * [x] Validar cpf
 * [x] Validar email
 *
 * **/

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder; // Injetado via construtor
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	/**fazendo de forma manual ate a implemteção do com oauthenticationManager **/
	@Override
	public LoginResponseDTO autenticar(LoginRequestDto loginRequest) {

		var auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()));
		UserDetails principal = (UserDetails) auth.getPrincipal();
		String token = jwtService.generateToken(principal);

		return LoginResponseDTO.builder()
				.email(principal.getUsername())
				.accessToken(token)
				.build();
	}

	@Override
	@Transactional
	public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequest) {
		/** se existe **/
		validarEmail(usuarioRequest.getEmail());
		validarCpf(usuarioRequest.getCpf());
//		usuarioRequest.setIsAtivo(true);
		/** Codifica antes de mapear para a entidade **/
		usuarioRequest.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
		/** agora que está convertido... **/
		Usuario entity = usuarioMapper.DtoToEntity(usuarioRequest);
		entity.setIsAtivo(true);
		/** ...chamo o repository para salvar o dto convertido para entidade **/
		Usuario salvarUsuario = usuarioRepository.save(entity);
		return usuarioMapper.entityToDto(salvarUsuario);
	}

	@Override
	public void validarUsuario(UsuarioRequestDTO usuarioDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarEmail(String email) {
		boolean existeEmail = usuarioRepository.existsByEmail(email);
		if (existeEmail) {
			throw new RegraNegocioException("Verifique seu email. Dado não permitido para cadastro.");
		}
	}

	@Override
	public void validarCpf(String cpf) {
		boolean existeCpf = usuarioRepository.existsByCpf(cpf);
		if (existeCpf) {
			throw new CpfAlreadyExistsException("verifique seu CPF, Dado não permitido para cadastro.");
		}
	}

	@Override
	public List<UsuarioRequestDTO> buscarTodosOsUsuariosCadastrados() {
		return null;
	}

	@Override
	public Optional<UsuarioRequestDTO> buscarUsuarioPorId(UUID uuid) {
		return null;
	}

	@Override
	public UsuarioRequestDTO atualizarUsuario(UsuarioRequestDTO usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UsuarioRequestDTO> buscarUsuarioPorEmail(String email) {
		return null;
	}

	@Override
	public Optional<UsuarioRequestDTO> buscarUsuarioPorNome(String nomeCompleto) {
		return null;//usuarioRepository.findByNomeCompleto(nomeCompleto).map(usuarioMapper::entityToRequestDto);
	}

	@Override
	public Optional<UsuarioRequestDTO> buscarUsuarioPorCpf(String cpf) {
		return null;//usuarioRepository.findByCpf(cpf).map(usuarioMapper::entityToRequestDto);
	}

	@Override
	public List<UsuarioRequestDTO> buscarUsuarioPorParteDoNome(String parteDoNome) {
		return null;
	}

	@Override
	public void deletarUsuario(UUID uuid) {
		usuarioRepository.deleteById(uuid);
	}

	@Override
	public Optional<UsuarioRequestDTO> obterUsuarioPorId(UUID uuid) {
		return buscarUsuarioPorId(uuid);
	}

	@Override
	public Optional<UsuarioRequestDTO> obterUsuarioPorCpf(String cpf) {
		return buscarUsuarioPorCpf(cpf);
	}	

	@Override
	public UsuarioRequestDTO atualizarPerfilUsuario(UsuarioRequestDTO usuario) {
		// TODO Auto-generated method stub
		return null;
	}
}
