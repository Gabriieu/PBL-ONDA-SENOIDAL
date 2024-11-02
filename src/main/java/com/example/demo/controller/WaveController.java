package com.example.demo.controller;

import com.example.demo.dto.wave.WaveCreateDTO;
import com.example.demo.dto.wave.WaveResponseDTO;
import com.example.demo.entity.Ponto;
import com.example.demo.entity.User;
import com.example.demo.entity.Wave;
import com.example.demo.exception.exceptions.UnauthorizedException;
import com.example.demo.jwt.JwtUserDetails;
import com.example.demo.service.PontoService;
import com.example.demo.service.UserService;
import com.example.demo.service.WaveService;
import com.example.demo.utils.Calculadora;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<Long> create(@RequestBody @Valid WaveCreateDTO dto,
                                       @AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userService.findById(userDetails.getId());
        Wave wave = new Wave(dto);
        wave.setUser(user);

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

        // OBS: diferente do convencional, esta rota retorna apenas o ID da entidade criada para facilitar no excel
        return ResponseEntity.status(HttpStatus.CREATED).body(simulacaoId);
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
        List<WaveResponseDTO> responses = ondas.stream().map(WaveResponseDTO::new).toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWave(@PathVariable Long id,
                                           @AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userService.findById(userDetails.getId());
        Wave wave = waveService.findById(id);

        if (!wave.getUser().equals(user) && !User.Role.ROLE_ADMIN.equals(user.getRole())) {
            throw new UnauthorizedException("Você não tem permissão para deletar esta simulação");
        }

        waveService.deleteWave(id);
        return ResponseEntity.noContent().build();
    }
}
