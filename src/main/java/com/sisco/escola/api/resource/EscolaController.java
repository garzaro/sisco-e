package com.sisco.escola.api.resource;


import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.EscolaJaCadastradaException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.EscolaService;
import jakarta.validation.Valid;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/escolas")
public class EscolaController {
    
    private static final Logger log = LoggerFactory.getLogger(EscolaController.class);
    public EscolaService escolaService;
    
    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }
    
    @PostMapping
    public ResponseEntity salvar(@Valid @RequestBody EscolaDTO dto) { /*usando @Valid,validação automatica, ler sobre*/
        /*logica do controlador*/
        try {
            Escola converterEntidade = converterDtoParaEntidade(dto); /*logica de conversao*/
            escolaService.salvarEscola(converterEntidade);
            return new ResponseEntity(converterEntidade, HttpStatus.CREATED);
            /*ou usar URI*/
            /*return ResponseEntity.created(URI
            		.create("/api/escolas/" + converterEntidade.getId()))
            		.build();*/
            
        } catch (EscolaJaCadastradaException e) {
            /*log para registrar as excessoes, ler sobre*/
            log.error("Escola já cadastrada: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
       
    }
    
    
    
    /*Um metodo para converter o dto em uma entidade de escola*/
    private Escola converterDtoParaEntidade(EscolaDTO dto){
        /*logica de conversao*/
        Escola escola = new Escola();
        escola.setId(dto.getId()); /*caso precise atualizar, ele vem preenchido com o id */
        escola.setNomeEscola(dto.getNomeEscola());
        escola.setCodigoEscola(dto.getCodigoEscola());
        escola.setCidadeEscola(dto.getCidadeEscola());
        escola.setBairroEscola(dto.getBairroEscola());
        escola.setTelefone(dto.getTelefone());
        return escola;
    }
}
