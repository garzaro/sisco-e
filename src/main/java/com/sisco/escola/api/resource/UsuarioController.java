package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Autentica um usuário a partir de e-mail e senha.
     * O serviço verifica as credenciais e retorna o DTO sem a senha.
     * Erros de autenticação são tratados pelo GlobalExceptionHandler (401).
     */
    @PostMapping("/autenticar")
    public ResponseEntity<UsuarioDTO> autenticar(@RequestBody UsuarioAutenticacaoDTO dto) {
        UsuarioDTO usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
        return ResponseEntity.ok(usuarioAutenticado);
    }

    /**
     * Cadastra um novo usuário.
     * O DTO é validado pelo Bean Validation antes de chegar ao serviço.
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler (422 / 409).
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO usuarioCriado = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler (422 / 409).
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Busca um usuário pelo seu ID.
     * Recurso não encontrado é tratado pelo GlobalExceptionHandler (422).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Desativa a conta de um usuário (soft-delete).
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioService.desativarConta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reativa a conta de um usuário.
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        usuarioService.ativarConta(id);
        return ResponseEntity.noContent().build();
    }
}
