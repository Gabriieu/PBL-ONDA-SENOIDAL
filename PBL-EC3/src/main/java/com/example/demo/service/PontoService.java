package com.example.demo.service;

import com.example.demo.entity.Ponto;
import com.example.demo.repository.PontoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoRepository pontoRepository;

    @Transactional
    public Ponto save(Ponto ponto) {
        return pontoRepository.save(ponto);
    }
}
