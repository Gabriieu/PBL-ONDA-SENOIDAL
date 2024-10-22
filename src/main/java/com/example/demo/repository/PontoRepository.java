package com.example.demo.repository;

import com.example.demo.entity.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

// DAO para a entidade Ponto
public interface PontoRepository extends JpaRepository<Ponto, Long> {

    @Procedure
    void SPINSEREPONTO(Long waveId, Double x, Double y, Double t);
}
