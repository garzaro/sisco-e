package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.EscolaJaCadastradaException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.EscolaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("api/escolas")
public class EscolaController {
    
    public EscolaService escolaService;
    
    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }
    
    /*@PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO dto) {
        Escola salvarEscola = criarEscola(dto);
        try {
            Escola escolaSalva = escolaService.salvar(salvarEscola);
            return new ResponseEntity (escolaSalva, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();
        } catch (EscolaJaCadastradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
    
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO dto) {
        Escola salvarEscola = criarEscola(dto);
        
        try {
            // Verifica se o nome da escola já está cadastrado
            if (escolaService.salvar(salvarEscola.getNomeEscola())); {
                throw new EscolaJaCadastradaException("Nome da escola já cadastrado.");
            }
            
            // Verifica se o código da escola já está cadastrado
            if (escolaService.buscarEscolaPorCodigo(salvarEscola.getNomeEscola())) {
                throw new EscolaJaCadastradaException("Código da escola já cadastrado.");
            }
            
            Escola escolaSalva = escolaService.salvar(salvarEscola);
            return new ResponseEntity<>(escolaSalva, HttpStatus.CREATED);
            /*ou usar url*/
            /*return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).build();*/
        } catch (EscolaJaCadastradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    
    /*para criação de instancia*/
    public static Escola criarEscola (EscolaDTO dto) {
        return Escola.builder()
                .nomeEscola(dto.getNomeEscola())
                .codigoEscola(dto.getCodigoEscola())
                .cidadeEscola(dto.getCidadeEscola())
                .bairroEscola(dto.getBairroEscola())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .build();
    }
    
    /*Escola.builder()
                .nomeEscola(dto.getNomeEscola())
                .codigoEscola(dto.getCodigoEscola())
                .cidadeEscola(dto.getCidadeEscola())
                .bairroEscola(dto.getBairroEscola())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .build();*/
    
}
