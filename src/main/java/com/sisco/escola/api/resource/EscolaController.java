package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
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
    public ResponseEntity salvar(@RequestBody EscolaDTO escolaDTO){
        try {
            Escola converterEntidade = converterDtoParaEntidade(escolaDTO);
            escolaService.salvar(converterEntidade);
            return new ResponseEntity(converterEntidade, HttpStatus.CREATED);

        }catch (EscolaJaCadastradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Escola converterDtoParaEntidade(EscolaDTO dto) {
        Escola converter = new Escola();
        converter.setNomeEscola(dto.getNomeEscola());
        converter.setCodigoEscola(dto.getCodigoEscola());
        converter.setCidadeEscola(dto.getCidadeEscola());
        converter.setBairroEscola(dto.getBairroEscola());
        converter.setEndereco(dto.getEndereco());
        converter.setTelefone(dto.getTelefone());
        return converter;
    }
    





    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    /*Um metodo para converter o dto em uma entidade de lancamento
    private Lancamento converterDtoParaEntidade(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId()); /*caso precise atualizar, ele vem preenchido com o id
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());
        /*inicio usuario
        /*receber o id do usuario, conforme dto
        Usuario buscarUsuario = usuarioService.obterUsuarioPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado com o id informado"));
        lancamento.setUsuario(buscarUsuario);
        /*fim usuario
        lancamento.setTipoLancamento(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;
    }*/
    
   
    
}
