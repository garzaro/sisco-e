package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.CodigoNotFoundException;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.EscolaNotFoundException;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.EscolaService;
import com.sisco.escola.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("api/escolas")
@RequiredArgsConstructor /*em substituição ao construtor dos servicos, *final*/
public class EscolaController {
    
    public final EscolaService escolaService;
    public final UsuarioService usuarioService;
    
    
    @PostMapping
    public ResponseEntity salvar(@RequestBody EscolaDTO escolaDTO){
        try {
            Escola converterEntidade = converterDtoParaEntidade(escolaDTO);
            escolaService.salvar(converterEntidade);
            return new ResponseEntity(converterEntidade, HttpStatus.CREATED);
        }catch (ErroValidacaoException e){
            return badRequest().body(e.getMessage());
        }
    }
    
    
    @PutMapping("atualizar")
    public ResponseEntity atualizarEscolaPorNomeOuCodigo(@RequestParam(value = "nome", required = false) String nome,
                                                         @RequestParam(value = "codigo", required = false) String codigo,
                                                         @RequestBody EscolaDTO escolaDTO) {
        /*verifica se ao menos um dos parâmetros foi informado*/
        if ((StringUtils.isEmpty(nome)) && StringUtils.isEmpty(codigo)) {
            return new ResponseEntity("Por favor, fornecer o nome ou código da escola", HttpStatus.BAD_REQUEST);
        }
        /*busca a escola por nome ou código*/
        Optional<Escola> escolaOptional = Optional.ofNullable(escolaService.buscar(nome, codigo));
        /*caso a escola seja encontrada, faz a atualização*/
        return escolaOptional.map(escolaExistente -> {
            try {
                Escola escolaAtualizada = converterDtoParaEntidade(escolaDTO);
                /*Mantém o ID da entidade existente*/
                escolaAtualizada.setId(escolaExistente.getId());
                escolaService.atualizar(escolaAtualizada);
                /*HttpStatus.OK, atualizações*/
                return new ResponseEntity(escolaAtualizada, HttpStatus.OK);
            } catch (EscolaNotFoundException e) {
                return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }).orElseGet(() -> new ResponseEntity<>("Escola não encontrada", HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable ("id") Long id){
        return escolaService.obterEscolaPorId(id).map(entity ->{
            escolaService.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( ()->
                new ResponseEntity("Escola não encontrada na base de dados", HttpStatus.BAD_REQUEST) );
    }
    
    @Transactional(readOnly = true)
    public List<Escola> listarEscolas(Pageable pageable) {
        return escolaService.listar((Escola) pageable);
    }
    
    /*Um metodo para converter o dto em uma entidade de escola*/
    private Escola converterDtoParaEntidade(EscolaDTO dto) {
        Escola escola = new Escola();
        /*escola.setId(dto.getId);*/
        escola.setNome(dto.getNomeEscola());
        escola.setCodigo(dto.getCodigoEscola());
        escola.setCidade(dto.getCidadeEscola());
        escola.setBairro(dto.getBairroEscola());
        escola.setEndereco(dto.getEndereco());
        escola.setTelefone(dto.getTelefone());
        return escola;
    }


 




}
