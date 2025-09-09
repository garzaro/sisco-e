package com.sisco.escola.service;


import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDate;

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

       // usuarioService.validarUsuario("clebergarzaro7@gmail.com");

    }
    
    @DisplayName("Erro lancado, ja existe email cadastrado")
    @Test
    public void testServiceDevelancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        Usuario salvarUsuario = testCriarUsuario();

        usuarioRepository.save(salvarUsuario);

        assertThrows(RegraDeNegocioException.class, () -> {
       //     usuarioService.validarUsuario("clebergarzaro7@gmail.com", null, null, null, null);
        });
    }

    public Usuario testCriarUsuario() {
        return Usuario.builder()
                .nome("Cleber Garzaro")
                .usuario("garzaro74")
                .cpf("123.456.789-00")
                .email("clebergarzaro74@gmail.com")
                .senha("senha")
                .dataCadastro(Instant.now())
                .build();
    }
}
