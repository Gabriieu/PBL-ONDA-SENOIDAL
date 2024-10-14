package com.example.demo.repository;

import com.example.demo.entity.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO para a entidade Ponto
public interface PontoRepository extends JpaRepository<Ponto, Long> {
}
