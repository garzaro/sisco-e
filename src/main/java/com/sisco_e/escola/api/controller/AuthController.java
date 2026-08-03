package com.sisco_e.escola.api.controller;

import com.sisco_e.escola.api.dto.AuthResponse;
import com.sisco_e.escola.api.dto.LoginRequestDto;
import com.sisco_e.escola.api.dto.RegisterRequestDto;
import com.sisco_e.escola.service.JwtService;
import com.sisco_e.escola.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDto request) {


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // If authentication is successful, load UserDetails and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String jwtToken = jwtService.gerarToken(userDetails);
        log.info("Usuário logado com sucesso: {}", request.email());
        return ResponseEntity.ok(new AuthResponse(jwtToken, "Bearer"));
    }
}
