package com.example.demo.entity;

import com.example.demo.dto.wave.WaveCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

// Entidade que representa uma onda
@Entity
// Tabela no banco de dados que armazena as ondas
@Table(name = "simulacoes")
// Gera os métodos getter em tempo de compilação
@Getter
// Gera os métodos setter em tempo de compilação
@Setter
// Gera um construtor sem argumentos
@NoArgsConstructor
public class Wave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double frequencia;

    private Double comprimentoOnda;

    private int duracao;

    private BigDecimal velocidade;

    private BigDecimal periodo;

    private Double erroMax;

    @CreationTimestamp
    private Date data;

    public Wave(WaveCreateDTO dto) {
        this.frequencia = dto.frequencia();
        this.comprimentoOnda = dto.comprimentoOnda();
        this.duracao = dto.segundos();
        this.erroMax = dto.erroMax();
    }
}
