package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EscolaRepositoryTest {
    
    @Autowired
    EscolaRepository escolaRepository;
    
    @Test
    public void deveVerificarAExistenciaDeUmEscolaNaBaseDeDados(){
        
        /*CENARIO*/
        Escola escolaDeTeste = Escola.builder()
                .nomeEscola("Marvin")
                .cidadeEscola("Fortaleza")
                .bairroEscola("Barra do Ceara")
                .build();
        escolaRepository.save(escolaDeTeste);
        
        /*AÇÃO*/
        Escola verificarSeExisteAEscola = escolaRepository.findByNomeEscola("Marvin");
        
        /*VERIFICAÇÃO*/
        Assertions.assertThat(verificarSeExisteAEscola).isNotNull();
        
    }
}
