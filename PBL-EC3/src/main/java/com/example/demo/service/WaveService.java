package com.example.demo.service;

import com.example.demo.dto.WaveUpdateDTO;
import com.example.demo.entity.Wave;
import com.example.demo.repository.WaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaveService {

    private final WaveRepository waveRepository;

    @Transactional
    public Wave save(Wave wave) {
        return waveRepository.save(wave);
    }

    public Wave findById(Long id) {
        return waveRepository.findById(id).orElseThrow(() -> new RuntimeException("Onda não encontrada"));
    }

    public List<Wave> findAll() {
        return waveRepository.findAll();
    }

    @Transactional
    public Wave updateWave(Long id, WaveUpdateDTO wave) {
        Wave waveToUpdate = findById(id);
        waveToUpdate.update(wave);
        return waveRepository.save(waveToUpdate);
    }

    public void  deleteWave(Long id) {
        Wave wave = findById(id);
        waveRepository.delete(wave);
    }
}
