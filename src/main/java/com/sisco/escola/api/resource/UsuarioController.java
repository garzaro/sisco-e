package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.exception.*;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/usuarios") /*uri para mapeamento de todas as requisições*/
public class UsuarioController {
    
    public UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario salvarUsuario = criarUsuario(dto);
        
        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(salvarUsuario);
            return new ResponseEntity (usuarioSalvo, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (ErroValidacaoException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }
    
    /*para criar instancias*/
    public static Usuario criarUsuario(UsuarioDTO dto){
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
        

