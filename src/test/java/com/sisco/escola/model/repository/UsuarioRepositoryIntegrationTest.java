package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryIntegrationTest {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    TestEntityManager testEntityManager;
    
    @Test
    @DisplayName("Verificação feita com sucesso - usuario buscado por email")
    public void testDeveRetornarVazioAoBuscarUsuarioPeloEmailSeNaoExistirNaBaseDeDados() {
        /*cenario*/
        /*execução*/
        Optional<Usuario> usuarioVazio = usuarioRepository
                .findByEmail("clebergarzaro74@gmail.com");
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
                .findByEmail("clebergarzaro74@gmail.com");
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
                .findById(criarUmaInstanciaERecuperarPeloId.getId()).orElseThrow();
        Assertions.assertThat(recuperarAInstanciaCriada).isNotNull();
        /*comparação*/
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeCompleto())
                .isEqualTo("Cleber Garzaro");
        Assertions.assertThat(recuperarAInstanciaCriada.getCpf())
                .isEqualTo("123.456.789-00");
        Assertions.assertThat(recuperarAInstanciaCriada.getEmail())
                .isEqualTo("clebergarzaro74@gmail.com");
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeUsuario())
                .isEqualTo("garzaro74");
        Assertions.assertThat(recuperarAInstanciaCriada.getSenha())
                .isEqualTo("senha");
        Assertions.assertThat(recuperarAInstanciaCriada.getDataCadastro())
                .isEqualTo(LocalDate.now());
    }
    
    /*para criação de instancia*/
    public static Usuario testCriarUsuario() {
        return Usuario.builder()
                .id(1l)
                .nomeCompleto("Cleber Garzaro")
                .nomeUsuario("garzaro74")
                .cpf("123.456.789-00")
                .email("clebergarzaro74@gmail.com")
                .senha("senha")
                .dataCadastro(LocalDate.now())
                .build();
    }
}
