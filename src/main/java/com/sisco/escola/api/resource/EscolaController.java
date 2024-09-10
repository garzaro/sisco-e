package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.api.dto.UsuarioCadastroDTO;
import com.sisco.escola.exception.CpfJaCadastradoException;
import com.sisco.escola.exception.EmailJaCadastradoException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/escolas")
public class EscolaController {
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO dto) {
        Escola salvarEscola = Escola.builder()
                .nomeEscola(dto.)
                .endereco(dto.)
         cadastroEscola
          cidadeEscola
          bairroEscola
          endereco
          telefone
                
                
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
}
