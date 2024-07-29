package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryIntegrationTest {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    TestEntityManager testEntityManager;
    
    @Test
    @DisplayName("Verificação feita com sucesso - email existe")
    public void deveVerificarAExistenciaDeUmEmail() {
        /*cenario*/
        Usuario usuarioDeTeste = testCriarUsuario();
        testEntityManager.persist(usuarioDeTeste);
        /*execução/ação*/
        boolean verificarSeExisteOEmail = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarSeExisteOEmail).isTrue();
    }
    
    @Test
    @DisplayName("Verificação feita com sucesso - false, nao existe cadastro com o email")
    public void deveRetornarFalsoQuandoNaoHouveUsuarioCadastradoComOEmail() {
        /*nao deve existir email na base de dados*/
        /*execução*/
        boolean verificarSeDeletouEmail = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarSeDeletouEmail).isFalse();
    }
    
    @Test
    @DisplayName("Verificação feita com sucesso - usuario buscado por email")
    public void testDeveRetornarVazioAoBuscarUsuarioPeloEmailSeNaoExistirNaBaseDeDados() {
        /*cenario*/
        /*execução*/
        Optional<Usuario> usuarioVazio = usuarioRepository
                .findByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(usuarioVazio.isPresent()).isFalse();
    }
    
    @Test
    @DisplayName("Verificação feita com sucesso - usuario salvo")
    public void testDeveSalvarUmUsuarioNaBaseDeDados() {
        /*salvar*/
        Usuario salvandoUsuario = testCriarUsuario();
        testEntityManager.persist(salvandoUsuario);
        /*ação*/
        Usuario usuarioPersistido = usuarioRepository.save(salvandoUsuario);
        /*verificação*/
        Assertions.assertThat(usuarioPersistido.getId()).isNotNull();
    }
    
    @Test
    @DisplayName("Verificação feita com sucesso - usuario buscado por email")
    public void testDeveBuscarUmUsuarioPeloEmail() {
        /*cenario*/
        Usuario persistindoUsuario = testCriarUsuario();
        testEntityManager.persist(persistindoUsuario);
        /*execução*/
        Optional<Usuario> usuarioPersistido = usuarioRepository
                .findByEmailLogin("clebergarzaro74@gmail.com");
        /*verificação*/
        Assertions.assertThat(usuarioPersistido.isPresent()).isTrue();
    }
    
    /*Recuperar um instancia de usuario pelo Id*/
    @Test
    @DisplayName("Verificação feita com sucesso - instancia recuperada pelo id")
    public void testParaRecuperarUmaInstanciaDeUsuarioPeloId() {
        /*cenario*/
        Usuario criarUmaInstanciaERecuperarPeloId = testCriarUsuario();
        testEntityManager.persist(criarUmaInstanciaERecuperarPeloId);
        /*verificação*/
        Usuario recuperarAInstanciaCriada = usuarioRepository
                .findById(criarUmaInstanciaERecuperarPeloId.getId())
                .orElseThrow();
        Assertions.assertThat(recuperarAInstanciaCriada).isNotNull();
        /*comparação*/
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeCompleto())
                .isEqualTo("Cleber Garzaro");
        Assertions.assertThat(recuperarAInstanciaCriada.getCadastroPessoaFisica())
                .isEqualTo("123.456.789-00");
        Assertions.assertThat(recuperarAInstanciaCriada.getEmailLogin())
                .isEqualTo("clebergarzaro74@gmail.com");
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeUsuario())
                .isEqualTo("garzaro74");
        Assertions.assertThat(recuperarAInstanciaCriada.getSenhaLogin())
                .isEqualTo("senha");
        Assertions.assertThat(recuperarAInstanciaCriada.getDataCadastro())
                .isEqualTo(LocalDate.now());
    }
    
    /*para criação de instancia*/
    public static Usuario testCriarUsuario() {
        return Usuario.builder()
                .nomeCompleto("Cleber Garzaro")
                .nomeUsuario("garzaro74")
                .cadastroPessoaFisica("123.456.789-00")
                .emailLogin("clebergarzaro74@gmail.com")
                .senhaLogin("senha")
                .dataCadastro(LocalDate.now())
                .build();
    }
}
