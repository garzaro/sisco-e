package com.sisco.escola.service;

import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @DisplayName("Usuario salvo com sucesso")
    @Test
    public void deveSalvarUsuarioNoBancoDeDados() {
        /**cenario*/
        Usuario usuario = criarUsuario();
        usuarioRepository.save(usuario);
    }

    @DisplayName("Verificar se o email existe")
    @Test
    public void deveValidarEmailQuandoEmailNaoExistir(){
        /**cenario*/
        usuarioRepository.deleteAll();
        usuarioService.validarEmailCpf("usuario@gmail.com","");
    }

    @DisplayName("Erro lancado, ja existe email cadastrado")
    @Test
    public void develancarErroValidandoEmailQuandoEmailJaCadastrado() {
        /**cenario**/
        Usuario usuario = criarUsuario();
        usuarioRepository.save(usuario);

        /**ação**/
        usuarioService.validarEmailCpf("usuario@gmail.com", "");
    }

    public Usuario criarUsuario() {
        return Usuario.builder()
                .nome("Cleber Garzaro")
                .usuario("garzaro74")
                .cpf("123.456.789-00")
                .email("usuario@gmail.com")
                .senha("S3nh4@123")
                .dataCadastro(LocalDateTime.now())
                .build();
    }
}
