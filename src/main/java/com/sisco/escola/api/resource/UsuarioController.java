package com.sisco.escola.api.resource;

import com.sisco.escola.api.converter.ConvertDtoToEntity;
import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.exception.*;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.function.Function;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ConvertDtoToEntity converter;

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioAutenticacaoDTO dto) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroDeAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario salvarUsuario = criarUsuario(dto);

        try {
            Usuario usuarioSalvo = usuarioService.salvar(salvarUsuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (ErroValidacaoException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        return usuarioService.obterUsuarioPorId(id).map(entity -> {
            try {
                Usuario usuario = converter.converterDtoParaEntidade(dto);
                usuario.setId(id);
                usuarioService.atualizar(usuario);
                return ResponseEntity.ok(usuario);

            } catch (ErroValidacaoException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(()-> new ResponseEntity(
                "O usuario com o ID " + "( " + id + " )" + " não foi encontrada.", HttpStatus.BAD_REQUEST));
    }

    /*para criar instancias*/
    public static Usuario criarUsuario(UsuarioDTO dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .usuario(dto.getUsuario())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .dataCadastro(LocalDate.now())
                .build();

    }
    /*@GetMapping("/teste")public String helloWorld() {return "Fala dev";}*/
}
        

