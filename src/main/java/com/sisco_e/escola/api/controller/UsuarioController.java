package com.sisco_e.escola.api.controller;

import com.sisco_e.escola.api.dto.UsuarioDTO;
import com.sisco_e.escola.service.JwtService;
import com.sisco_e.escola.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") //usuario
@RequiredArgsConstructor
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    /**
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler por exemplo.
     * O Controller apenas recebe o DTO e delega ao Service. Nao deve saber dos detalhes da entidade e servico
     * Cadastra um novo usuário.
     */
    @PostMapping("/join/sign-up")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto){

        UsuarioDTO criarUsuario = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario);
    }
}
