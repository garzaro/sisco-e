package com.sisco.escola.api.resource;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.sisco.escola.model.entity.Usuario;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void deveProcurarTodosOsUsuarios() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/usuarios").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveRetornarTodosOsUsuariosClassificadoPorNome() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/usuarios").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].name").value("Usuario 1"));
        resultActions.andExpect(jsonPath("$.name", containsInAnyOrder("Usuario 1", "Usuario 2", "Usuario 3")));
    }

    @Test
    @Transactional
    public void deveSalvarUmUsuario() throws Exception {
        Usuario usuario = Usuario.builder()
                .nome("Usuario 1")
                .email("[EMAIL_ADDRESS]")
                .password("123456")
                .build();
        
        ResultActions resultActions = mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(asJsonString(usuario)));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void deveAtualizarUmUsuario() throws Exception {
        Usuario usuario = Usuario.builder()
                .nome("Usuario 1")
                .email("[EMAIL_ADDRESS]")
                .password("123456")
                .build();
        
        ResultActions resultActions = mockMvc.perform(put("/usuarios/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(usuario)));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveDeletarUmUsuario() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/usuarios/1").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveRetornarUmUsuarioPorId() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/usuarios/1").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveRetornarUmUsuarioPorEmail() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/usuarios/email/[EMAIL_ADDRESS]").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    public void deveRetornarUmUsuarioPorPerfil() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/usuarios/perfil/ADMIN").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveAutenticarUmUsuario() throws Exception {
        Usuario usuario = Usuario.builder()
                .email("[EMAIL_ADDRESS]")
                .password("123456")
                .build();
        
        ResultActions resultActions = mockMvc.perform(post("/usuarios/autenticar").contentType(MediaType.APPLICATION_JSON).content(asJsonString(usuario)));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deveDesautenticarUmUsuario() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/usuarios/desautenticar").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }      
}
