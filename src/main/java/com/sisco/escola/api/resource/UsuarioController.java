package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioCadastroDTO;
import com.sisco.escola.exception.CpfJaCadastradoException;
import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.EmailJaCadastradoException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    /*Salvar - Este método é um endpoint que recebe uma requisição HTTP POST*/
    /*ResponseEntity representa o corpo da resposta*/
    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioCadastroDTO dto) {
        Usuario salvarUsuario = Usuario.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .cadastroPessoaFisica(dto.getCadastroPessoaFisica())
                .nomeUsuario(dto.getNomeUsuario())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .dataCadastro(dto.getDataCadastro())
                .build();
        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(salvarUsuario);
            return new ResponseEntity (usuarioSalvo, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (EmailJaCadastradoException | CpfJaCadastradoException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }
    /*@GetMapping("/teste")public String helloWorld() {return "Fala dev";}*/
}
        

