package com.sisco_e.escola.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**Controlador do dominio/recurso de usuario
 *
 * [] Atualizar dados cadastrais (PUT /users/me ou /users/{id})
 * [] Cadastro de usuario
 * [] Alterar senha (enquanto já está logado)
 * [] Alterar senha (enquanto não logado)
 * [] Listar usuários (se for um painel administrativo)
 * [] Buscar dados do próprio perfil (GET /users/me)
 *
 * /users/me          -> UserController (Retorna os dados do usuário logado)
 * /users/profile     -> UserController (Atualiza endereço, nome, etc.)
 * /users/{id}        -> UserController (Busca ou deleta usuário - Admin)
 * **/

@RestController
@RequestMapping("/api/usuario") //usuario
@RequiredArgsConstructor
public class UsuarioController {

//    @GetMapping("/me")
//    public ResponseEntity<UserResponseDTO> getMyProfile(Principal principal) {
//        // 'principal.getName()' retorna o e-mail ou username que estava no Token JWT!
//        String email = principal.getName();
//
//        // Busca no banco e retorna os dados do usuário logado
//        // ...
//    }
}
