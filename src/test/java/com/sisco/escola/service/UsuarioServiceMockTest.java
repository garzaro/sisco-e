package com.sisco.escola.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Usuario;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceMockTest {	

    @Autowired
    private MockMvc mockMvc;
    
    
	
//	@DisplayName("Email invalido")
//    @Test
//    public void testServiceDevelancarErroAoValidarEmailQuandoEmailForInvalido() {
//        Usuario usuario = Usuario.builder()
//                .email("usuario.email")                
//                .build();
//        usuario.setEmail("usuario.email");
//        assertThrows(ErroValidacaoException.class, () -> {
//            usuarioService.validarEmail("usuario.email");
//        });
//    }

//    @DisplayName("Cpf invalido")
//    @Test
//    public void testServiceDevelancarErroAoValidarCpfQuandoCpfForInvalido() {
//        assertThrows(ErroValidacaoException.class, () -> {
//            usuarioService.validarCpf("clebergarzaro74@gmail.com");
//        });
//    }

}
