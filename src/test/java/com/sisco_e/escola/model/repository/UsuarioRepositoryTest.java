package com.sisco_e.escola.model.repository;

import com.sisco_e.escola.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de Repositório — UsuarioRepository
 *
 * Usa @DataJpaTest: sobe apenas a camada JPA (H2 em memória),
 * sem controllers, services ou filtros de segurança.
 *
 * Cenários cobertos:
 *  1. existsByEmail        — email presente / ausente
 *  2. existsByCpf          — CPF presente / ausente
 *  3. findByEmail          — encontrado / não encontrado
 *  4. findByCpf            — encontrado / não encontrado
 *  5. findByNomeCompleto   — encontrado / não encontrado
 *  6. findByUsername       — encontrado / não encontrado
 *  7. findByNomeCompletoContainingIgnoreCase — busca parcial
 *  8. buscarUsuarioDigitandoApenasParteDoNome — JPQL parcial
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("UsuarioRepository — Testes de Repositório")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .nomeCompleto("Maria da Silva")
                .username("maria.silva")
                .email("maria@gmail.com")
                .cpf("12345678901")
                .password("$2a$10$hashed")
                .isAtivo(true)
                .build();
        em.persistAndFlush(usuario);
    }

    // =========================================================================
    // 1. existsByEmail
    // =========================================================================
    @Nested
    @DisplayName("1. existsByEmail")
    class ExistsByEmail {

        @Test
        @DisplayName("deve retornar true quando email já está cadastrado")
        void deveRetornarTrueParaEmailExistente() {
            assertThat(usuarioRepository.existsByEmail("maria@gmail.com")).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando email não está cadastrado")
        void deveRetornarFalseParaEmailInexistente() {
            assertThat(usuarioRepository.existsByEmail("naoexiste@gmail.com")).isFalse();
        }
    }

    // =========================================================================
    // 2. existsByCpf
    // =========================================================================
    @Nested
    @DisplayName("2. existsByCpf")
    class ExistsByCpf {

        @Test
        @DisplayName("deve retornar true quando CPF já está cadastrado")
        void deveRetornarTrueParaCpfExistente() {
            assertThat(usuarioRepository.existsByCpf("12345678901")).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando CPF não está cadastrado")
        void deveRetornarFalseParaCpfInexistente() {
            assertThat(usuarioRepository.existsByCpf("99999999999")).isFalse();
        }
    }

    // =========================================================================
    // 3. findByEmail
    // =========================================================================
    @Nested
    @DisplayName("3. findByEmail")
    class FindByEmail {

        @Test
        @DisplayName("deve encontrar usuário pelo email")
        void deveEncontrarUsuarioPorEmail() {
            Optional<Usuario> resultado = usuarioRepository.findByEmail("maria@gmail.com");

            assertThat(resultado).isPresent();
            assertThat(resultado.get().getNomeCompleto()).isEqualTo("Maria da Silva");
        }

        @Test
        @DisplayName("deve retornar Optional vazio para email inexistente")
        void deveRetornarOptionalVazioParaEmailInexistente() {
            Optional<Usuario> resultado = usuarioRepository.findByEmail("naoexiste@gmail.com");

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================================
    // 4. findByCpf
    // =========================================================================
    @Nested
    @DisplayName("4. findByCpf")
    class FindByCpf {

        @Test
        @DisplayName("deve encontrar usuário pelo CPF")
        void deveEncontrarUsuarioPorCpf() {
            Optional<Usuario> resultado = usuarioRepository.findByCpf("12345678901");

            assertThat(resultado).isPresent();
        }

        @Test
        @DisplayName("deve retornar Optional vazio para CPF inexistente")
        void deveRetornarOptionalVazioParaCpfInexistente() {
            Optional<Usuario> resultado = usuarioRepository.findByCpf("00000000000");

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================================
    // 5. findByNomeCompleto
    // =========================================================================
    @Nested
    @DisplayName("5. findByNomeCompleto")
    class FindByNomeCompleto {

        @Test
        @DisplayName("deve encontrar usuário pelo nome completo exato")
        void deveEncontrarPorNomeCompleto() {
            Optional<Usuario> resultado = usuarioRepository.findByNomeCompleto("Maria da Silva");

            assertThat(resultado).isPresent();
            assertThat(resultado.get().getEmail()).isEqualTo("maria@gmail.com");
        }

        @Test
        @DisplayName("deve retornar Optional vazio para nome inexistente")
        void deveRetornarOptionalVazioParaNomeInexistente() {
            Optional<Usuario> resultado = usuarioRepository.findByNomeCompleto("Fulano de Tal");

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================================
    // 6. findByUsername
    // =========================================================================
    @Nested
    @DisplayName("6. findByUsername")
    class FindByUsername {

        @Test
        @DisplayName("deve encontrar usuário pelo username")
        void deveEncontrarPorUsername() {
            Optional<Usuario> resultado = usuarioRepository.findByUsername("maria.silva");

            assertThat(resultado).isPresent();
        }

        @Test
        @DisplayName("deve retornar Optional vazio para username inexistente")
        void deveRetornarVazioParaUsernameInexistente() {
            Optional<Usuario> resultado = usuarioRepository.findByUsername("naoexiste");

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================================
    // 7. findByNomeCompletoContainingIgnoreCase
    // =========================================================================
    @Nested
    @DisplayName("7. Busca parcial por nome (Query Method)")
    class BuscaParcialNomeQueryMethod {

        @Test
        @DisplayName("deve encontrar usuários com parte do nome (case-insensitive)")
        void deveBuscarPorParteDoNomeCaseInsensitive() {
            List<Usuario> resultado =
                    usuarioRepository.findByNomeCompletoContainingIgnoreCase("silva");

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getEmail()).isEqualTo("maria@gmail.com");
        }

        @Test
        @DisplayName("deve retornar lista vazia quando nenhum nome contém o trecho")
        void deveRetornarListaVaziaQuandoNadaEncontrado() {
            List<Usuario> resultado =
                    usuarioRepository.findByNomeCompletoContainingIgnoreCase("xyz_naoexiste");

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================================
    // 8. buscarUsuarioDigitandoApenasParteDoNome (JPQL)
    // =========================================================================
    @Nested
    @DisplayName("8. Busca parcial por nome (JPQL)")
    class BuscaParcialNomeJpql {

        @Test
        @DisplayName("JPQL — deve encontrar usuário digitando apenas parte do nome")
        void deveBuscarComJpqlPorParteDoNome() {
            List<Usuario> resultado =
                    usuarioRepository.buscarUsuarioDigitandoApenasParteDoNome("maria");

            assertThat(resultado).isNotEmpty();
            assertThat(resultado.get(0).getNomeCompleto()).containsIgnoringCase("Maria");
        }

        @Test
        @DisplayName("JPQL — deve retornar lista vazia quando parte do nome não existe")
        void deveRetornarListaVaziaComJpql() {
            List<Usuario> resultado =
                    usuarioRepository.buscarUsuarioDigitandoApenasParteDoNome("zzznaoexiste");

            assertThat(resultado).isEmpty();
        }
    }
}
