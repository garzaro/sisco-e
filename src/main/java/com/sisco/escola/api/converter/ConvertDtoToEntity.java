package com.sisco.escola.api.converter;

import com.sisco.escola.api.dto.EscolaDTO;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertDtoToEntity {
    @Autowired
    UsuarioService usuarioService;
    
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
}
