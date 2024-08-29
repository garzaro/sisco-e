package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.UsuarioAutenticacaoDTO;
import com.sisco.escola.api.dto.UsuarioCadastroDTO;
import com.sisco.escola.exception.ErroDeAutenticacao;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/usuarios") /*para mapeamento de todas as requisições*/
public class UsuarioController {
    
    public UsuarioService usuarioService;
        
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping("/autenticar")
    public ResponseEntity<Object> autenticarUsuario(@RequestBody UsuarioAutenticacaoDTO dtoAuth) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(
                    dtoAuth.getEmailLogin(),
                    dtoAuth.getSenhaLogin());
            return ResponseEntity.ok(usuarioAutenticado);
            
        } catch (ErroDeAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /*Salvar - Este método é um endpoint que recebe uma requisição HTTP POST*/
    /*ResponseEntity representa o corpo da resposta*/
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = Usuario.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .cadastroPessoaFisica(dto.getCadastroPessoaFisica())
                .nomeUsuario(dto.getNomeUsuario())
                .emailLogin(dto.getEmailLogin())
                .senhaLogin(dto.getSenhaLogin())
                .dataCadastro(dto.getDataCadastro())
                .build();
        
        try {
            Usuario usuarioSalvo = usuarioService.persistirUsuario(usuario);
            return new ResponseEntity<Object>(usuarioSalvo, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (RegraDeNegocioException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }
    /*@GetMapping("/teste")public String helloWorld() {return "Fala dev";}*/
}
        

