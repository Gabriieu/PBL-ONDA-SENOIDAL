package com.example.demo.entity;

import com.example.demo.dto.WaveCreateDTO;
import com.example.demo.dto.WaveUpdateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "simulacoes")
@Getter
@Setter
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

    @UpdateTimestamp
    private Date dataAtualizacao;

    public Wave(WaveCreateDTO dto) {
        this.frequencia = dto.frequencia();
        this.comprimentoOnda = dto.comprimentoOnda();
        this.duracao = dto.segundos();
        this.velocidade = BigDecimal.valueOf(dto.frequencia() * dto.comprimentoOnda()).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.periodo = BigDecimal.valueOf(1 / dto.frequencia()).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.erroMax = dto.erroMax();
    }

    public void update(WaveUpdateDTO dto) {
        this.frequencia = dto.frequencia();
        this.comprimentoOnda = dto.comprimentoOnda();
        this.duracao = dto.segundos();
        this.erroMax = dto.erroMax();
    }
}
