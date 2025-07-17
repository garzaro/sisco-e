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
@Table(name = "provedor", schema = "siscoescola")
public class Provedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "provedor_internet", length = 30)
    private String provedorInternet;
    
    @Column(name = "ip_link", length = 15)
    private String ipLink;
    
    @Column(name = "mascara_rede", length = 15)
    private String mascaraRede;
    
    @Column(name = "gateway_provedor", length = 15)
    private String gatewayProvedor;

    @Column(name = "dns_um", length = 45)
    private String dns1;

    @Column(name = "dns_dois", length = 45)
    private String dns2;
    
    @Column(name = "velocidade_link", length = 20)
    private String velocidadeLink;
    
    /*VER ENUM DO PROJETO DE FINANCAS*/
    @Column(name = "status_link")
    private String statusLink;
    
    @ManyToOne
    @JoinColumn(name = "idEscola")
    private Escola escola;
    
    /*getters e setters*/
}
