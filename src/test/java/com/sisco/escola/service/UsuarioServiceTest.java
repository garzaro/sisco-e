package com.sisco.escola.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    private Validator validator;
    
    
	@SuppressWarnings("null")
	@Test
    @DisplayName("deve criar usuario com dados validos")
    public void deveCriarUsuarioComDadosValidos() {
    	//cenario    	
    	Usuario usuario = criarUsuario();
    	usuarioRepository.save(usuario);   	
    }
	
	@SuppressWarnings("null")
	@Test
    @DisplayName("deve falhar ao validar Usuario com todos os campos nulos")
    public void deveCriarUsuarioComConstrutorVazio() {
    	//cenario - criacao do objeto    	
    	Usuario usuario = Usuario.builder().build();
    	
    	//Execução 
    	Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario); 
    	
    	//Verificação 
    	assertThat(violations).isNotEmpty();
    	assertThat(violations).hasSize(6);    	
    }
	
	@SuppressWarnings("null")
	@Test
    @DisplayName("deve ter id nulo antes de salvar")
	public void deveInicializarIdComoNullPorPadrao() {
		Usuario usuario = criarUsuario();
		
		assertThat(usuario.getId()).isNull();				
	}
    
    @DisplayName("Email validado com sucesso")
    @Test
    public void testDeveValidarEmailNaBase() {
    	/**cenario**/
    	usuarioRepository.deleteAll();
    	/**ação**/
        usuarioService.validarEmail("usuario@email.com");
    }

//    @DisplayName("Erro lancado, ja existe email cadastrado")
//    @Test
//    public void testServiceDevelancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
//        Usuario salvarUsuario = testCriarUsuario();
//
//        usuarioRepository.save(salvarUsuario);
//
//       
//            usuarioService.validarEmail("usuario@email.com");
//        });
//    }    
    
    public Usuario criarUsuario() {
        return Usuario.builder()
                .nome("Cleber Garzaro")
                .usuario("garzaro74")
                .cpf("111.444.777-35")
                .email("clebergarzaro74@gmail.com")
                .password("Senha@123")
                .dataCadastro(Instant.now())
                .build();
    }
}
