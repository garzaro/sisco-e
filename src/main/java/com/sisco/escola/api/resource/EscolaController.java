package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.EscolaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        }catch (ErroValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EscolaDTO escolaDTO) {
        return escolaService.obterEscolaPorId(id).map(entity -> {
            Escola escola = converterDtoParaEntidade(escolaDTO);
            escola.setId(entity.getId());
            escolaService.atualizar(escola);
            return ResponseEntity.ok(escola);
        }).orElseGet(() -> new ResponseEntity("Escola não encontrada", HttpStatus.BAD_REQUEST));
    }

    private Escola converterDtoParaEntidade(EscolaDTO dto) {
        Escola escola = new Escola();
        escola.setId(dto.getId);
        escola.setNome(dto.getNomeEscola());
        escola.setCodigo(dto.getCodigoEscola());
        escola.setCidade(dto.getCidadeEscola());
        escola.setBairro(dto.getBairroEscola());
        escola.setEndereco(dto.getEndereco());
        escola.setTelefone(dto.getTelefone());
        return escola;
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
