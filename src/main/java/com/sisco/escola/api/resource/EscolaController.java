package com.sisco.escola.api.resource;

import com.sisco.escola.api.converter.ConvertDtoToEntity;
import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.EscolaService;
import com.sisco.escola.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@RestController
@RequiredArgsConstructor
@RequestMapping("api/escolas")
public class EscolaController {
    
    private final EscolaService escolaService;
    private final UsuarioService usuarioService;
    private final ConvertDtoToEntity converter;

    /**total de escolas cadastras**/
    @GetMapping("/total")
    public ResponseEntity<Long> quantidadeEscolas(){
        long quantidade = escolaService.totalEscola();
        return ResponseEntity.ok(quantidade);
    }
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO dto) {
    	
        try {
        	
            Escola converterEntidade = converter.toEntity(dto);
            converterEntidade = escolaService.salvarEscola(converterEntidade);
            return new ResponseEntity(converterEntidade, HttpStatus.CREATED);
            
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EscolaDTO dto) {
        /* entity é resultado da busca do service pelo id */
        return escolaService.obterEscolaPorId(id).map(entity -> {
            try {
                Escola escola = converter.toEntity(dto);
                escola.setId(id);
                escolaService.atualizarEscola(escola);
                return ResponseEntity.ok(escola);
                
            } catch (RegraDeNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("A escola com o ID " + "( " + id + " )" + " não foi encontrada.",
                HttpStatus.BAD_REQUEST));
    }
    
    /*@PutMapping("{id}")
    public void atualizar(@PathVariable("id") String id, @RequestBody Produto produto){
        produto.setId(id);
        produtoRepository.save(produto);
    }*/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return escolaService.obterEscolaPorId(id).map(entity -> {
            escolaService.deletarEscola(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() ->
                new ResponseEntity("Escola não encontrada na base de dados", HttpStatus.BAD_REQUEST));
    }
    
    @Transactional(readOnly = true)
    public List<Escola> listarEscolas(Pageable pageable) {
        return escolaService.listarEscolas((Escola) pageable);
    }
}
