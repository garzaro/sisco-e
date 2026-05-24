package com.sisco_e.escola.api.controller;

import com.sisco_e.escola.api.dto.UsuarioDTO;
import com.sisco_e.escola.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Cadastra um novo usuário.
     * O DTO é validado pelo Bean Validation antes de chegar ao serviço.
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler por exemplo.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto){

        UsuarioDTO criarUsuario = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario);
    }
}
