package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "checkpoint", schema = "siscoescola")
public class Checkpoint {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nome_checkpoint", length = 30)
    private String nomeCheckpoint;
    
    @Column(name = "mac_address", length = 17)
    private String macAddress;
    
    @Column(name = "serial_number", length = 50)
    private String serialNumber;
    
    @Column(name = "rede_lan", length = 100)
    private String redeLan;
    
    @Column(name = "range_dhcp", length = 50)
    private String rangeDHCP;
    
    @Column(name = "dns_um", length = 45)
    private String dns1;
    
    @Column(name = "dns_dois", length = 45)
    private String dns2;
    
    /*RELACIONAMENTO*/
    @ManyToOne
    @JoinColumn(name = "idEscola")
    private Escola nomeEscola;
    
    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
