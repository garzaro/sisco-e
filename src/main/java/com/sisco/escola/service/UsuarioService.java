package com.sisco.escola.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sisco.escola.api.dto.UsuarioDTO;

public interface UsuarioService {
	
	UsuarioDTO cadastrar(UsuarioDTO usuarioDTO);
	
	UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO);
	
	UsuarioDTO buscarPorId(Long id);
	
	void deletar(Long id);
	
	Page<UsuarioDTO> listarTodos(Pageable pageable);
	
	UsuarioDTO autenticar(String email, String senha);
	
	UsuarioDTO buscarPorEmail(String email);
	
	UsuarioDTO buscarPorUsername(String username);
	
	UsuarioDTO buscarPorCpf(String cpf);
	
	void redefinirSenha(Long id, String newPassword);
	
	void ativarConta(Long id);
	
	void desativarConta(Long id);
	
	/**verificar se ja existe**/
	void validarEmail(String email);
	
	void validarCpf(String cpf);
	
	void validarUsername(String username);

}
