package com.example.demo.dto;

import com.example.demo.entity.Wave;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record WaveUpdateDTO(
        @DecimalMin(value = "0.01", message = "Frequência deve ser maior que 0")
        @DecimalMax(value = "0.50", message = "Frequência deve ser menor ou igual a 0.5")
        Double frequencia,

        @JsonProperty("comprimento_onda")
        @DecimalMin(value = "0.20", message = "Comprimento de onda deve ser maior que 0.2")
        @DecimalMax(value = "2.00", message = "Comprimento de onda não pode ser maior que 2.0")
        Double comprimentoOnda,

        @Min(value = 1, message = "Segundos deve ser maior ou igual 1")
        @Max(value = 10, message = "Segundos deve ser menor ou igual a 10")
        int segundos,

        @JsonProperty("erro_max")
        @DecimalMin(value = "0.01", message = "Erro máximo deve ser maior que 0.01")
        @NotNull(message = "Erro máximo não pode ser nulo")
        Double erroMax
) {
    public WaveUpdateDTO(Wave wave) {
        this(wave.getFrequencia(), wave.getComprimentoOnda(), wave.getDuracao(), wave.getErroMax());
    }
}
