package com.example.demo.service;

import com.example.demo.entity.Wave;
import com.example.demo.repository.WaveRepository;
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

    public Long save(Wave wave) {
        return waveRepository.SPINSERESIMULACAO(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao(), wave.getErroMax());
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
