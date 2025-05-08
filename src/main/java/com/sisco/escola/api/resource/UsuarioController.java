package com.sisco.escola.api.resource;

import com.sisco.escola.api.converter.ConvertDtoToEntity;
import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.exception.*;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.function.Function;

@RestController
@RequestMapping("api/usuarios") /*uri para mapeamento de todas as requisições*/
public class UsuarioController {

    public UsuarioService usuarioService;
    public ConvertDtoToEntity converter;

    public UsuarioController(UsuarioService usuarioService, ConvertDtoToEntity converter) {
        this.usuarioService = usuarioService;
        this.converter = converter;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticarUsuario(@RequestBody UsuarioAutenticacaoDTO dtoAuth) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(dtoAuth.getEmail(), dtoAuth.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroDeAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*Salvar - Este metodo é um endpoint que recebe uma requisição HTTP POST*/
    /*ResponseEntity representa o corpo da resposta*/
    @PostMapping
    public ResponseEntity salvar(@Valid @RequestBody UsuarioDTO dto) {
        Usuario salvarUsuario = criarUsuario(dto);
        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(salvarUsuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (ErroValidacaoException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        return usuarioService.obterUsuarioPorId(id).map(entity -> {
            try {
                Usuario usuario = converter.converterDtoParaEntidade(dto);
                usuario.setId(id);
                usuarioService.atualizarUsuario(usuario);
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
                .nomeCompleto(dto.getNomeCompleto())
                .nomeUsuario(dto.getNomeUsuario())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .dataCadastro(LocalDate.now())
                .build();

    }

    /*@GetMapping("/teste")public String helloWorld() {return "Fala dev";}*/
}
        

