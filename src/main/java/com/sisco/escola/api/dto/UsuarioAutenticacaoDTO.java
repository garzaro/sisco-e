package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioAutenticacaoDTO {
    private String emailLogin;
    private String senhaLogin;
}
