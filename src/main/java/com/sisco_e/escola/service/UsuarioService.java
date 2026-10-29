package com.sisco_e.escola.service;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.LoginRequestDto;
import com.sisco_e.escola.api.dto.request.UsuarioRequestDTO;
import com.sisco_e.escola.api.dto.response.LoginResponseDTO;
import com.sisco_e.escola.api.dto.response.UsuarioResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {
	/*Autenticao do usuario na base*/
	LoginResponseDTO autenticar(LoginRequestDto loginRequest);

	UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequest);

	/**validar ver se ja existe usuario na base*/
    void validarUsuario(UsuarioRequestDTO usuarioDto);
    
    /*ver se existe o email ou email na base*/
    void validarEmail(String email);
    
    /*ver se existe o cpf na base*/
    void validarCpf(String cpf);

	/*Buscar todos os usuarios cadastrados na base de dados*/
	List<UsuarioRequestDTO> buscarTodosOsUsuariosCadastrados();

	/*Buscar o usuario por uuid na base*/
	Optional<UsuarioRequestDTO> buscarUsuarioPorId(UUID uuid);
	
	/*atualizar o usuario*/
	UsuarioRequestDTO atualizarUsuario(UsuarioRequestDTO usuario);

	/*Buscar o usuario por email na base*/
	Optional<UsuarioRequestDTO> buscarUsuarioPorEmail(String email);

	/*Buscar o usuario por nome na base*/
	Optional<UsuarioRequestDTO> buscarUsuarioPorNome(String nomeCompleto);
	
	/*Buscar o usuario por cpf na base*/
	Optional<UsuarioRequestDTO> buscarUsuarioPorCpf(String cpf);
	
	/*Buscar o usuario por parte do nome na base*/
	List<UsuarioRequestDTO> buscarUsuarioPorParteDoNome(String parteDoNome);
	
	/*Deletar o usuario da base*/
	void deletarUsuario(UUID uuid);
	
	/*Obter o usuario por uuid na base*/
	Optional<UsuarioRequestDTO> obterUsuarioPorId(UUID uuid);
	
	/*Obter o usuario por cpf na base*/
	Optional<UsuarioRequestDTO> obterUsuarioPorCpf(String cpf);
	
	/*Atualizar as informações do usuario*/
	UsuarioRequestDTO atualizarPerfilUsuario(UsuarioRequestDTO usuario);

}
