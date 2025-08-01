package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class EscolaRepositoryTest {
    
    @Autowired
    EscolaRepository escolaRepository;
    
    @Test
    public void deveVerificarAExistenciaDeUmEscolaNaBaseDeDados(){
        
        /*CENARIO*/
        Escola escolaDeTeste = Escola.builder()
                .nome("Marvin")
                //.cidade("Fortaleza")
                .bairro("Barra do Ceara")
                .build();
        escolaRepository.save(escolaDeTeste);
        
        /*AÇÃO*/
        boolean verificarSeExisteAEscola = escolaRepository.existsByNomeAndIdNot("Marvin", null);
        
        /*VERIFICAÇÃO*/
        Assertions.assertThat(verificarSeExisteAEscola).isNotNull();
        
    }
}
