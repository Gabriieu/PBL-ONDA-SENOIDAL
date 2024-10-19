package com.example.demo.service;

import com.example.demo.entity.Wave;
import com.example.demo.repository.WaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Define a classe como um serviço
@Service
// Gera um construtor com todas as dependências
@RequiredArgsConstructor
public class WaveService {

    // Injeção de dependência
    private final WaveRepository waveRepository;

    @Transactional
    public Long save(Wave wave) {
        return waveRepository.SP_INSERE_SIMULACAO(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao(), wave.getErroMax());
    }

    public Wave findById(Long id) {
        return waveRepository.findById(id).orElseThrow(() -> new RuntimeException("Onda não encontrada"));
    }

    public List<Wave> findAll() {
        return waveRepository.findAll();
    }

    public void  deleteWave(Long id) {
        Wave wave = findById(id);
        waveRepository.delete(wave);
    }
}
