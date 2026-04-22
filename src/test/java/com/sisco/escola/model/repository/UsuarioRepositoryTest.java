package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

/**TESTE DE INTEGRACAO**/

@DataJpaTest
//@Profile("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("email encontrado")
    public void deveVerificarAExistenciaDeUmEmail(){
        /*cenario*/
        Usuario usuario = criarUsuario();
        testEntityManager.persist(usuario);
        /*execução*/
        boolean verificarEmail = usuarioRepository.existsByEmail("usuario@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarEmail).isTrue();
    }
    
    @SuppressWarnings("null")
	@Test
    @DisplayName("email encontrado")
    public void deveVerificarExistenciaDeUmEmailNoBancoDeDados(){
        /*cenario*/
        Usuario usuario = criarUsuario();
        usuarioRepository.save(usuario);
        /*execução*/
        boolean verificarEmail = usuarioRepository.existsByEmail("usuario@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarEmail).isTrue();
    }


    @Test
    @DisplayName("falso, nao tem usuario cadastrado")
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoPeloEmail(){
        /*cenario*/
        /*usuarioRepository.deleteAll();*/
        /*execução*/
        boolean verificarEmailNaBase = usuarioRepository.existsByEmail("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarEmailNaBase).isFalse();
    }

    @Test
    @DisplayName("retornou vazio")
    public void deveRetornarVazioAoBuscarUsuarioPeloEmailSeNaoExisteNaBase(){
        /*cenario*/
        /*vazio*/
        /*ação*/
        Optional<Usuario> verUsuarioNaBase = usuarioRepository.findByEmail("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verUsuarioNaBase.isPresent()).isFalse();
    }
    
    @Test
    @DisplayName("usuario retornado por email")
    public void deveBuscarUmUsuarioPorEmail() {
        /*cenario*/
        Usuario persistirUsuario = criarUsuario();
        testEntityManager.persist(persistirUsuario);
        /*execução*/
        Optional<Usuario> buscarUsuario = usuarioRepository.findByEmail("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(buscarUsuario.isPresent()).isTrue();
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
    @DisplayName("instancia recuperada pelo id")
    public void deveRetornarUmaInstanciaDeUsuarioPeloId() {
        /*cenario*/
        Usuario usuarioEsperado = criarUsuario();
        usuarioRepository.save(usuarioEsperado);
        /*ação*/
        Usuario recuperarUsuario = usuarioRepository.findById(usuarioEsperado.getId()).orElseThrow();
        /*verificação*/
        Assertions.assertThat(recuperarUsuario.getId()).isNotNull();
        Assertions.assertThat(recuperarUsuario.getNome()).isEqualTo("Cleber Garzaro"); /*comparação*/
        Assertions.assertThat(recuperarUsuario.getCpf()).isEqualTo("111.444.777-35");
        Assertions.assertThat(recuperarUsuario.getUsuario()).isEqualTo("garzaro74");
        Assertions.assertThat(recuperarUsuario.getEmail()).isEqualTo("clebergarzaro74@gmail.com");
        Assertions.assertThat(recuperarUsuario.getPassword()).isEqualTo("Senha@123");
        Assertions.assertThat(recuperarUsuario.getDataCadastro()).isNotNull();
    }
    
    /*para criação de instancia*/
        public static Usuario criarUsuario () {
            return Usuario.builder()
                    .nome("Cleber Garzaro")
                    .usuario("garzaro74")
                    .cpf("111.444.777-35")
                    .email("usuario@gmail.com")
                    .password("Senha@123")
                    .dataCadastro(Instant.now())
                    .build();
        }
    }

