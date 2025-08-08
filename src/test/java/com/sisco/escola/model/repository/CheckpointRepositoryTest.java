package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Checkpoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CheckpointRepositoryTest {
    
    @Autowired
    CheckpointRepository checkpointRepository;
    
    @Test
    public void deveVerificarAExistenciDeUmCheckpointNaBaseDeDados(){
        
        /*CENARIO*/
        Checkpoint checkpointDeTeste = Checkpoint.builder()
                .nomeCheckpoint("Checkpoint da escola")
                .macAddress("78-6A-BF-52-CA-7D")
                //.dns1("192.168.1.1")
                //.dns2("192.168.1.10")
                .rangeDHCP("192.168.1.1 - 192.168.1.20")
                .serialNumber("ADB452812")
                .redeLan("192.168.1.0")
                .build();
        checkpointRepository.save(checkpointDeTeste);
        
        /*EXECUÇÃO/AÇÃO*/
        boolean verificarSeOCheckpointExiste = checkpointRepository
                .existsByNomeCheckpoint("Checkpoint da escola");
        
        /*VERIFICAÇÃO*/
        Assertions.assertThat(verificarSeOCheckpointExiste).isTrue();
      
    }
    
    /*NOME CHECKPOINT*/
    @Test
    public void deveVerificarAExistenciaDeUmCheckpointPeloMac(){
        /*cenario*/
        Checkpoint nomeCheckpointDeTeste = Checkpoint.builder()
                .nomeCheckpoint("Checkpoint da escola")
                .macAddress("78-6A-BF-52-CA-8D")
                .build();
        checkpointRepository.save(nomeCheckpointDeTeste);
        
        /*ação*/
        boolean deveExistirUmMac = checkpointRepository.existsByMacAddress("78-6A-BF-52-CA-8D");
        
        /*verificação*/
        Assertions.assertThat(deveExistirUmMac).isTrue();
   }
    
    /*SERIAL NUMBER CHECKPOINT*/
    @Test
    public void deveVerificarAExistenciaDeUmCheckpointPelosSerial() {
        /*cenario*/
        Checkpoint serialCheckpointDeTeste = Checkpoint.builder()
                .nomeCheckpoint("Checkpoint da escola")
                .serialNumber("ADB452813")
                .build();
        checkpointRepository.save(serialCheckpointDeTeste);
        
        /*ação*/
        boolean deveExistirUmSerial = checkpointRepository.existsBySerialNumber("ADB452813");
        
        /*verificação*/
        Assertions.assertThat(deveExistirUmSerial).isTrue();
    }
}
