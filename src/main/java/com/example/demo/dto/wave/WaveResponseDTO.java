package com.example.demo.dto.wave;

import com.example.demo.entity.Wave;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.Date;

// DTO (Data Transfer Object) para a resposta de uma onda
@JsonPropertyOrder({"id", "frequencia", "comprimentoOnda", "segundos", "erroMax", "data"})
public record WaveResponseDTO(
        Long id,
        BigDecimal velocidade,
        BigDecimal periodo,
        Double frequencia,
        @JsonProperty("comprimento_onda")
        Double comprimentoOnda,
        int segundos,
        @JsonProperty("erro_max")
        Double erroMax,
        @JsonProperty("data_criacao")
        Date data
) {
    public WaveResponseDTO(Wave wave) {
        this(
                wave.getId(),
                wave.getVelocidade(),
                wave.getPeriodo(),
                wave.getFrequencia(),
                wave.getComprimentoOnda(),
                wave.getDuracao(),
                wave.getErroMax(),
                wave.getData()
        );
    }
}
