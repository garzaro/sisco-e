package com.sisco.escola.model.repository;




import com.sisco.escola.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    TestEntityManager testEntityManager;
    
    @Test
    public void deveVerificarAExistenciaDeUmEmailNaBaseDeDados(){
        
        /*cenario*/
        Usuario usuarioDeTeste = Usuario.builder()
                .nomeUsuario("Cleber Garzaro")
                .emailLogin("clebergarzaro74@gmail.com")
                .build();
        testEntityManager.persist(usuarioDeTeste);
        
        /*execução/ação*/
        boolean verificarSeExisteOEmail = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");
        
        /*verificação*/
        Assertions.assertThat(verificarSeExisteOEmail).isTrue();
       
    }
    
    @Test
    public void deveRetornarFalsoQuandoNaoHouveUsuarioCadastradoComOEmail(){
        /*nao deve existir email na base de dados*/
        /*execução*/
        boolean verificarSeDeletouEmail = usuarioRepository.existsByEmailLogin("clebergarzaro74@gmail.com");
        
        /*verificação*/
        Assertions.assertThat(verificarSeDeletouEmail).isFalse();
   }
   
   /*Recuperar um instancia de usuario pelo Id*/
    @Test
    public void testeParaRecuperarUmaInstanciaDeusuarioPeloId(){
        /*cenario*/
        Usuario criarUmaInstanciaDeUsuarioParaSerRecuperadaPeloId = Usuario.builder()
                .nomeCompleto("Cleber Garzaro")
                .cadastroPessoaFisica("24800126894")
                .emailLogin("clebergarzaro74@gmail.com")
                .nomeUsuario("garzaro74")
                .senhaLogin("123456")
                .dataCadastro(LocalDate.now())
                .build();
        testEntityManager.persist(criarUmaInstanciaDeUsuarioParaSerRecuperadaPeloId);
        
        /*verificação*/
        Usuario recuperarAInstanciaCriada = usuarioRepository
                .findById(criarUmaInstanciaDeUsuarioParaSerRecuperadaPeloId.getId())
                .orElseThrow();
        Assertions.assertThat(recuperarAInstanciaCriada).isNotNull();
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeCompleto()).isEqualTo("Cleber Garzaro");
        Assertions.assertThat(recuperarAInstanciaCriada.getCadastroPessoaFisica()).isEqualTo("24800126894");
        Assertions.assertThat(recuperarAInstanciaCriada.getEmailLogin()).isEqualTo("clebergarzaro74@gmail.com");
        Assertions.assertThat(recuperarAInstanciaCriada.getNomeUsuario()).isEqualTo("garzaro74");
        Assertions.assertThat(recuperarAInstanciaCriada.getSenhaLogin()).isEqualTo("123456");
        Assertions.assertThat(recuperarAInstanciaCriada.getDataCadastro()).isEqualTo(LocalDate.now());
   }
    
    
}
