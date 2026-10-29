package com.sisco_e.escola.service.impl;

import com.sisco_e.escola.api.dto.request.LoginRequestDto;
import com.sisco_e.escola.api.dto.request.UsuarioRequestDTO;
import com.sisco_e.escola.api.dto.response.LoginResponseDTO;
import com.sisco_e.escola.api.dto.response.UsuarioResponseDTO;
import com.sisco_e.escola.mapper.UsuarioMapper;
import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private String email = "usuario@gmail.com";
    private String password = "password123";

    @BeforeEach
    public void setUp() {
        usuario = Usuario.builder()
                .uuid(UUID.randomUUID())
                .email(email)
                .password("encoded_password")
                .nomeCompleto("Usuario Teste")
                .build();
    }

    @Test
    public void deveAutenticarComSucesso() {
        // Cenario
        LoginRequestDto loginRequest = LoginRequestDto.builder().email(email).password(password).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(password, "encoded_password")).thenReturn(true);
        UsuarioResponseDTO responseDto = UsuarioResponseDTO.builder().email(email).nomeCompleto("Usuario Teste").build();
        Mockito.when(usuarioMapper.entityToDto(usuario)).thenReturn(responseDto);

        // Acao
        LoginResponseDTO result = usuarioService.autenticar(loginRequest);

        // Verificacao
        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getUsuario().getEmail());
        Assertions.assertEquals("mock-token", result.getToken());
    }

    @Test
    public void deveLancarErroQuandoUsuarioNaoEncontrado() {
        // Cenario
        LoginRequestDto loginRequest = LoginRequestDto.builder().email(email).password(password).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Acao & Verificacao
        BadCredentialsException exception = Assertions.assertThrows(BadCredentialsException.class, () -> {
            usuarioService.autenticar(loginRequest);
        });

        Assertions.assertEquals("Verifique seu email", exception.getMessage());
    }

    @Test
    public void deveLancarErroQuandoSenhaInvalida() {
        // Cenario
        LoginRequestDto loginRequest = LoginRequestDto.builder().email(email).password(password).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(password, "encoded_password")).thenReturn(false);

        // Acao & Verificacao
        BadCredentialsException exception = Assertions.assertThrows(BadCredentialsException.class, () -> {
            usuarioService.autenticar(loginRequest);
        });

        Assertions.assertEquals("Verifique sua senha", exception.getMessage());
    }
}
