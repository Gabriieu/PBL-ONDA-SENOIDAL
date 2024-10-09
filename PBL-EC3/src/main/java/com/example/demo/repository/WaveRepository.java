package com.example.demo.repository;

import com.example.demo.entity.Wave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaveRepository extends JpaRepository<Wave, Long> {
}
