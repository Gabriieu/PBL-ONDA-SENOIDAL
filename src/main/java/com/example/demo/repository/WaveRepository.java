package com.example.demo.repository;

import com.example.demo.entity.Wave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

// DAO para a entidade Wave
public interface WaveRepository extends JpaRepository<Wave, Long> {

    //  Inserção de uma simulação via procedure
    @Procedure
    Long SP_INSERE_SIMULACAO(Double frequencia, Double comprimentoOnda, Integer duracao, Double erroMax);
}
