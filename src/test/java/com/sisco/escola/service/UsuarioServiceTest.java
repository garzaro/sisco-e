package com.sisco.escola.service;


import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @DisplayName("Email validado com sucesso, nao existe email")
    @Test
    public void testDeveValidarEmailNaBase() {
        usuarioRepository.deleteAll();
        usuarioService.validarEmailLoginNaBaseDeDados("clebergarzaro7@gmail.com");
        Assertions.assertThat(usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com"));
    }
    
    @DisplayName("Erro lancado, ja existe email cadastrado")
    @Test
    public void testServiceDevelancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        Usuario salvarUsuario = Usuario.builder()
                .nomeUsuario("garzaro74")
                .emailLogin("clebergarzaro74@gmail.com")
                .build();
        usuarioRepository.save(salvarUsuario);
        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.validarEmailLoginNaBaseDeDados("clebergarzaro74@gmail.com");
        });
    }
}
