package com.sisco.escola.model.entity;

import jakarta.persistence.*;

public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="")
    private String type;
    @Column(name="")
    private String number;
    
    @ManyToOne
    private Escola school;

}
