package com.example.demo.service;

import com.example.demo.entity.Wave;
import com.example.demo.exception.exceptions.EntityNotFoundException;
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

    public Long save(Wave wave) {
        return waveRepository.SPINSERESIMULACAO(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao(), wave.getErroMax(), wave.getUser().getId());
    }

    @Transactional
    public Wave findById(Long id) {
        Wave response = waveRepository.SPOBTEMSIMULACAO(id);

        if(response != null){
            return  response;
        }

        throw new EntityNotFoundException(String.format("Onda %d não encontrada", id));
    }

    public List<Wave> findAll() {
        return waveRepository.findAll();
    }

    @Transactional
    public void  deleteWave(Long id) {
        Wave wave = findById(id);
        waveRepository.delete(wave);
    }
}
