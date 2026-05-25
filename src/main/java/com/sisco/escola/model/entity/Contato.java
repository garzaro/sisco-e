package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "contato", schema = "siscoescola")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Contato é obrigatório")
    @Column(name="telefone")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

    @NotBlank(message = "Selecione um tipo de contato")
    @Size(max = 20, message = "Tipo de contato deve ter no máximo 20 caracteres")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_contato", nullable = false, length = 20)
    private TipoContato tipoContato;

}
