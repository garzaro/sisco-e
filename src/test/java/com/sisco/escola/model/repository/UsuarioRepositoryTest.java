package com.sisco.escola.model.repository;


import com.sisco.escola.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

//@DataJpaTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Profile("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("email encontrado")
    public void deveVerificarAExistenciaDeUmEmail(){
        /*cenario*/
        Usuario usuario = criarUsuario();
        usuarioRepository.save(usuario);

        /*execução*/
        boolean verificarEmail = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");

        /*verificação*/
        Assertions.assertThat(verificarEmail).isTrue();
    }

    @Test
    @DisplayName("falso, nao tem usuario cadastrado")
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoPeloEmail(){
        /*cenario*/
        usuarioRepository.deleteAll();
        /*execução*/
        boolean verificarEmailNaBase = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarEmailNaBase).isFalse();
    }

    @Test
    @DisplayName("retornou vazio")
    public void deveRetornarVazioAoBuscarUsuarioPeloEmailSeNaoExisteNaBase(){
        /*cenario*/
        usuarioRepository.deleteAll();
        /*ação*/
        Optional<Usuario> verUsuarioNaBase = usuarioRepository.findByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verUsuarioNaBase.isPresent()).isFalse();
    }

    @Test
    @DisplayName("usuario salvo")
    public void deveSalvarUmUsuarioNoBancoDeDados(){
        /*cenario*/
        Usuario salvarUsuario = criarUsuario();
        /*execução*/
        Usuario usuarioSalvo = usuarioRepository.save(salvarUsuario);
        /*verificação*/
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }
    
    @Test
    public void deveBuscarUmUsuarioPorEmail() {
    	/*cenario*/
    	Usuario persistirUsuario = criarUsuario();
    	usuarioRepository.save(persistirUsuario);
    	/*execução*/
    	Optional<Usuario> buscarUsuario = usuarioRepository.findByEmailLogin("clebergarzaro74@gmail.com");
    	/*verificação*/
    	Assertions.assertThat(buscarUsuario.isPresent()).isTrue();
    }


    /*para criação de instancia*/
    public static Usuario criarUsuario() {
        return Usuario.builder()
                .id(1l)
                .nomeCompleto("Cleber Garzaro")
                .nomeUsuario("garzaro74")
                .cadastroPessoaFisica("123.456.789-00")
                .emailLogin("clebergarzaro74@gmail.com")
                .senhaLogin("senha")
                .dataCadastro(LocalDate.now())
                .build();
    }
}
