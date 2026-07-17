package com.sisco.escola.security;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adapter que implementa {@link UserDetails} do Spring Security.
 *
 * Encapsula a entidade {@link Usuario} e expõe os dados necessários
 * para o Spring Security autenticar e autorizar requisições.
 *
 * Por design, o Spring Security trabalha com {@code UserDetails},
 * não com entidades de negócio. Esta classe atua como uma ponte
 * entre o domínio (Usuario) e a infraestrutura de segurança.
 */
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
        /**Por enquanto, nenhuma role/authority explícita
         * Se houver roles/permissões no futuro, carregue aqui
         * **/
        this.authorities = List.of(new SimpleGrantedAuthority("USER")); //authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities; //List.of();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }
    /**
     * O Spring Security usa o "username" como identificador único.
     * Neste caso, usamos o email como username (padrão para login).
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return usuario.getIsAtivo(); // UserDetails.super.isEnabled();
    }

    /**
     * Getter para acessar a entidade Usuario se necessário.
     */
    public Usuario getUsuario() {
        return usuario;
    }
}
