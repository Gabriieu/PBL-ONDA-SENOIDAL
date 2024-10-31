package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// Entidade que representa um ponto de uma onda
@Entity
// Tabela no banco de dados que armazena os pontos
@Table(name = "pontos")
// Gera os métodos getter em tempo de compilação
@Getter
// Gera os métodos setter em tempo de compilação
@Setter
public class Ponto {

    // Identificador do ponto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coordenada x do ponto
    @Column(nullable = false)
    private Double x;

    // Coordenada y do ponto
    @Column(nullable = false)
    private Double y;

    // Tempo do ponto
    @Column(nullable = false)
    private Double t;

    // Onda a qual o ponto pertence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wave_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wave wave;
}
