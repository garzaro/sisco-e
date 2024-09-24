package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.service.EscolaService;
import com.sisco.escola.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("api/escolas")
@RequiredArgsConstructor /*em substituição ao construtor dos servicos, *final*/
public class EscolaController {
    
    public final EscolaService escolaService;
    public final UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "codigo", required = false) String codigo
    ){
        
        /*checar nulidade, nome e codigo*/
        if (Optional.ofNullable(nome).isPresent() || Optional.ofNullable(codigo).isPresent()) {
            return ResponseEntity.badRequest().body("Por favor, informe o nome ou codigo da escola.");
        }
        Escola escola = new Escola();
        escola.setNome(nome);
        escola.setCodigo(codigo);
        
        Optional<Escola> buscarEscola = escolaService.buscarPorNome(escola);
        
        return ResponseEntity.ok(buscarEscola);
    }
    
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
    
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EscolaDTO escolaDTO) {
        return escolaService.obterEscolaPorId(id).map(entity -> {
            try {
                Escola escola = converterDtoParaEntidade(escolaDTO);
                escola.setId(entity.getId());
                escolaService.atualizar(escola);
                return new ResponseEntity(escola, HttpStatus.CREATED);
            }catch (RegraDeNegocioException e){
                return badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Escola não encontrada", HttpStatus.BAD_REQUEST));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable ("id") Long id){
        return escolaService.obterEscolaPorId(id).map(entity ->{
            escolaService.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( ()->
                new ResponseEntity("Escola não encontrada na base de dados", HttpStatus.BAD_REQUEST) );
    }
    
    /*Um metodo para converter o dto em uma entidade de lancamento*/
    private Escola converterDtoParaEntidade(EscolaDTO dto) {
        Escola escola = new Escola();
        escola.setId(dto.getId);
        escola.setNome(dto.getNomeEscola());
        escola.setCodigo(dto.getCodigoEscola());
        escola.setCidade(dto.getCidadeEscola());
        escola.setBairro(dto.getBairroEscola());
        escola.setEndereco(dto.getEndereco());
        escola.setTelefone(dto.getTelefone());
        
        Usuario usuario = usuarioService.obterUsuarioPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado."));
        escola.setUsuario(usuario);
        
        return escola;
    }
}
