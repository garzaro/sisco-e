package com.sisco_e.escola.security;

import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.model.repository.ProvedorInternetRepository;
import com.sisco_e.escola.model.repository.UsuarioRepository;
import com.sisco_e.escola.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("SecurityIntegrationTest - Testes de Integração de Segurança")
class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private EscolaRepository escolaRepository;

    @MockitoBean
    private ProvedorInternetRepository provedorInternetRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("I-01: rota protegida sem token deve retornar 403")
    void rotaProtegida_SemToken_Retorna403() throws Exception {
        mockMvc.perform(get("/api/escola"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("I-02: rota protegida com token malformado deve retornar 403")
    void rotaProtegida_TokenInvalido_Retorna403() throws Exception {
        mockMvc.perform(get("/api/escola")
                .header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("I-04: rota pública deve retornar 200 sem token")
    void rotaPublica_SemToken_Retorna200() throws Exception {
        // /v3/api-docs é pública conforme SecurityConfig (ou pelo menos permitAll)
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("I-06: login com senha errada deve retornar 401")
    void login_SenhaErrada_Retorna401() throws Exception {
        String email = "admin@test.com";
        Usuario usuario = Usuario.builder()
                .email(email)
                .password("$argon2id$v=19$m=65536,t=3,p=1$c29tZXNhbHQ$P1/S+8n/S+8n/S+8n/S+8n/S+8n/S+8n/S+8n/S+8") // hash fake
                .isAtivo(true)
                .build();
        
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        String loginJson = "{\"email\":\"" + email + "\", \"password\":\"senha-errada-longa-suficiente\"}";
        mockMvc.perform(post("/api/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized());
    }
}
