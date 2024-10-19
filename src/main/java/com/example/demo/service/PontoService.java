package com.example.demo.service;

import com.example.demo.entity.Ponto;
import com.example.demo.repository.PontoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Classe de serviço para salvar um ponto
@Service
// Gera um construtor com todas as dependências
@RequiredArgsConstructor
public class PontoService {

    // Injeção de dependências do repositório de pontos
    private final PontoRepository pontoRepository;

    // Salva um ponto
    public void save(Ponto ponto) {
        pontoRepository.SP_INSERE_PONTO(ponto.getWave().getId(), ponto.getX(), ponto.getY(), ponto.getT());
    }
}
