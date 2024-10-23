package com.example.demo.controller;

import com.example.demo.dto.wave.WaveCreateDTO;
import com.example.demo.dto.wave.WaveResponseDTO;
import com.example.demo.entity.Ponto;
import com.example.demo.entity.Wave;
import com.example.demo.service.PontoService;
import com.example.demo.service.WaveService;
import com.example.demo.utils.Calculadora;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Define a classe como um controller REST
@RestController
// Define a rota base para a API
@RequestMapping("/onda")
// Gera um construtor com todas as dependências
@RequiredArgsConstructor
public class WaveController {

    // Injeção de dependências
    private final WaveService waveService;
    private final PontoService pontoService;
    private final Calculadora calculadora;

    @PostMapping
    @Transactional
    public ResponseEntity<Long> create(@RequestBody @Valid WaveCreateDTO dto) {
        Wave wave = new Wave(dto);

        // Salva a simulação e obtém o ID gerado
        Long simulacaoId = waveService.save(wave);

        // Definir o ID no objeto wave
        wave.setId(simulacaoId);

        // Calcula e salva os pontos associados à simulação
        List<Ponto> pontos = calculadora.calculaY(wave);
        for (Ponto ponto : pontos) {
            ponto.setWave(wave);
            pontoService.save(ponto);
        }

        return ResponseEntity.ok(simulacaoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaveResponseDTO> getWave(@PathVariable Long id) {
        Wave wave = waveService.findById(id);
        WaveResponseDTO responseDto = new WaveResponseDTO(wave);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<WaveResponseDTO>> getWaves() {
        List<Wave> ondas = waveService.findAll();
        List<WaveResponseDTO> responseDtos = ondas.stream().map(WaveResponseDTO::new).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWave(@PathVariable Long id) {
        waveService.deleteWave(id);
        return ResponseEntity.noContent().build();
    }
}
