package com.sisco.escola.api.converter;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConvertDtoToEntity {
    /*@Autowired
    UsuarioService usuarioService;*/

    /* Um metodo para converter o dto em uma entidade de escola */
    public Escola converterDtoParaEntidade(EscolaDTO dto) {
        Escola escola = new Escola();
        escola.setId(dto.getId()); /* caso precise atualizar, ele vem preenchido com o id */
        escola.setNome(dto.getNome());
        escola.setCodigo(dto.getCodigo());
        escola.setCidade(dto.getCidade());
        escola.setBairro(dto.getBairro());
        escola.setEndereco(dto.getEndereco());
        escola.setTelefone(dto.getTelefone());
        return escola;
    }

    /* Um metodo para converter o dto em uma entidade de usuario */
    public Usuario converterDtoParaEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId()); /* caso precise atualizar, ele vem preenchido com o id */
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setUsuario(dto.getUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setDataCadastro(dto.getDataCadastro());
        return usuario;
    }
}
