package com.sisco_e.escola.service;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {
//	UsuarioDto cadastrarUsuario(UsuarioDto UsuarioDto);
	
	/*Autenticao do usuario na base*/
	UsuarioDTO autenticar(String email, String password);

	UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDto);

	/**validar ver se ja existe usuario na base*/
    void validarUsuario(UsuarioDTO usuarioDto);
    
    /*ver se existe o email ou email na base*/
    void validarEmail(String email);
    
    /*ver se existe o cpf na base*/
    void validarCpf(String cpf);

	/*Buscar todos os usuarios cadastrados na base de dados*/
	List<UsuarioDTO> buscarTodosOsUsuariosCadastrados();

	/*Buscar o usuario por id na base*/
	Optional<UsuarioDTO> buscarUsuarioPorId(UUID uuid);
	
	/*atualizar o usuario*/
	UsuarioDTO atualizarUsuario(UsuarioDTO usuario);

	/*Buscar o usuario por email na base*/
	Optional<UsuarioDTO> buscarUsuarioPorEmail(String email);

	/*Buscar o usuario por nome na base*/
	Optional<UsuarioDTO> buscarUsuarioPorNome(String nomeCompleto);
	
	/*Buscar o usuario por cpf na base*/
	Optional<UsuarioDTO> buscarUsuarioPorCpf(String cpf);
	
	/*Buscar o usuario por parte do nome na base*/
	List<UsuarioDTO> buscarUsuarioPorParteDoNome(String parteDoNome);
	
	/*Deletar o usuario da base*/
	void deletarUsuario(UUID uuid);
	
	/*Obter o usuario por id na base*/
	Optional<UsuarioDTO> obterUsuarioPorId(UUID uuid);
	
	/*Obter o usuario por cpf na base*/
	Optional<UsuarioDTO> obterUsuarioPorCpf(String cpf);
	
	/*Atualizar as informações do usuario*/
	UsuarioDTO atualizarPerfilUsuario(UsuarioDTO usuario);

}
