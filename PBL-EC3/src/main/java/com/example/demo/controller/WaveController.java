package com.example.demo.controller;

import com.example.demo.dto.WaveCreateDTO;
import com.example.demo.dto.WaveResponseDTO;
import com.example.demo.dto.WaveUpdateDTO;
import com.example.demo.entity.Ponto;
import com.example.demo.entity.Wave;
import com.example.demo.service.PontoService;
import com.example.demo.service.WaveService;
import com.example.demo.utils.Calculadora;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onda")
@RequiredArgsConstructor
public class WaveController {

    private final WaveService waveService;
    private final PontoService pontoService;
    private final Calculadora calculadora;


    @PostMapping()
    public ResponseEntity<WaveResponseDTO> create(@RequestBody @Valid WaveCreateDTO dto) {
        Wave wave = new Wave(dto);
        waveService.save(wave);
        List<Ponto> pontos = calculadora.calculaY(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao());
        for (Ponto ponto : pontos) {
            ponto.setWave(wave);
            pontoService.save(ponto);
        }

        return ResponseEntity.ok(new WaveResponseDTO(wave));
    }

    @PostMapping("/vba")
    public ResponseEntity<Long> createVBA(@RequestBody @Valid WaveCreateDTO dto) {
        Wave wave = new Wave(dto);
        waveService.save(wave);
        List<Ponto> pontos = calculadora.calculaY(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao());
        for (Ponto ponto : pontos) {
            ponto.setWave(wave);
            pontoService.save(ponto);
        }

        return ResponseEntity.ok(wave.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaveResponseDTO> getWave(@PathVariable Long id) {
        Wave wave = waveService.findById(id);
        WaveResponseDTO responseDto = new WaveResponseDTO(wave);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/print")
    public ResponseEntity<WaveResponseDTO> getWavePdf(@PathVariable Long id) {
        // IMPLEMENTAR GERAÇÃO DE PDF COM JASPERSOFT
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

    @PutMapping("/{id}")
    public ResponseEntity<WaveResponseDTO> updateWave(@PathVariable Long id, @RequestBody @Valid WaveUpdateDTO dto) {
        return ResponseEntity.ok(new WaveResponseDTO(waveService.updateWave(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWave(@PathVariable Long id) {
        waveService.deleteWave(id);
        return ResponseEntity.noContent().build();
    }
}
