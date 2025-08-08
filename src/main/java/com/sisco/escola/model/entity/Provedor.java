package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O provedor é obrigatório")
    @Size(max = 50, message = "Provedor deve ter no máximo 50 caracteres")
    @Column(name = "provedor_internet", nullable = false, length = 30)
    private String provedorInternet;

    @NotBlank(message = "IP é obrigatório")
    @Size(max = 39, message = "Internet Protocol deve ter no maximo 39 caracteres")
    @Column(name = "ip_link", nullable = false, length = 39)
    private String ipLink;

    @NotBlank(message = "A máscara é obrigatótio")
    @Size(max = 5, message = "A máscara deve ter no máximo 5 caracteres")
    @Column(name = "mascara_rede", nullable = false, length = 5)
    private String mascaraRede;

    @NotBlank(message = "O gateway é obrigatório")
    @Size(max = 39, message = "O gateway deve ter no máximo 39 caracteres")
    @Column(name = "gateway_provedor", nullable = false, length = 39)
    private String gatewayProvedor;

    @NotBlank(message = "DNS é obrigatório")
    @Size(max = 39, message = "O DNS deve ter no máximo 39 caracteres")
    @Column(name = "dns_um", nullable = false, length = 39)
    private String dns1;

    @Size(max = 39, message = "O DNS deve ter no máximo 39 caracteres")
    @Column(name = "dns_dois", nullable = false, length = 39)
    private String dns2;

    @Size(max = 39, message = "O DNS deve ter no máximo 39 caracteres")
    @Column(name = "dns_tres", nullable = false, length = 39)
    private String dns3;

    @NotBlank(message = "A velocidade do link é obrigatório")
    @Size(max = 10, message = "A velocidade do link deve ter no máximo 10 caracteres")
    @Column(name = "velocidade_link", nullable = false, length = 10)
    private String velocidadeLink;

    @ManyToOne
    @JoinColumn(name = "idEscola")
    private Escola escola;

    @NotBlank(message = "O status do link é obrigatório")
    @Size(max = 4, message = "O status do link deve ter no máximo 4 caracteres")
    @Column(name = "status_link", nullable = false, length = 4)
    private StatusLink statusLink;

    /*getters e setters*/
}
