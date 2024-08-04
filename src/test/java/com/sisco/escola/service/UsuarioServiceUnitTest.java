package com.sisco.escola.service;


import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceUnitTest {
    
    UsuarioService usuarioService;
    UsuarioRepository  usuarioRepository;
    
    @BeforeAll
    public void setUp(){
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    
    }
    
    
}
