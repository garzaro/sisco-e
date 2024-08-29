package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Builder
@Getter
@Setter
public class UsuarioAutenticadoDTO {
    private String emailLogin;
    private String senhaLogin;
}
