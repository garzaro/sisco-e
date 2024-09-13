package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.CodigoJaCadastradoException;
import com.sisco.escola.exception.EscolaJaCadastradaException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.EscolaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/escolas")
public class EscolaController {
    
    public EscolaService escolaService;
    
    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO dto) {
        Escola salvarEscola = Escola.builder()
                .nomeEscola(dto.getNomeEscola())
                .codigoEscola(dto.getCodigoEscola())
                .cidadeEscola(dto.getCidadeEscola())
                .bairroEscola(dto.getBairroEscola())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .build();
        try {
            Escola escolaSalva = escolaService.salvar(salvarEscola);
            return new ResponseEntity (escolaSalva, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (EscolaJaCadastradaException mensagemDeErro) {
            return ResponseEntity.badRequest().body(mensagemDeErro.getMessage());
        }
    }
}
