package com.sisco.escola.config;

import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.security.UsuarioDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    /**
     * Carrega os dados do usuário pelo email (username no contexto de segurança).
     *
     * @param email email do usuário (usado como "username" pelo Spring Security)
     * @return {@link UserDetails} contendo usuário e suas authorities
     * @throws UsernameNotFoundException se o usuário não existir ou estiver inativo
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> usuario.getIsAtivo())
                .map(UsuarioDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado: " + email
                ));
    }
}
