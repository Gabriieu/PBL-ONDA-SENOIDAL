package com.example.demo.dto.wave;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

// DTO (Data Transfer Object) para a criação de uma onda
public record WaveCreateDTO(
        @DecimalMin(value = "0.01", message = "Frequência deve ser maior que 0.01 Hz")
        @DecimalMax(value = "30000.00", message = "Frequência deve ser menor que 30000.00 Hz")
        Double frequencia,

        @JsonProperty("comprimento_onda")
        @DecimalMin(value = "0.01", message = "Comprimento de onda deve ser maior que 0.01 m")
        @DecimalMax(value = "10.00", message = "Comprimento de onda deve ser menor que 10.00 m")
        Double comprimentoOnda,

        @Min(value = 1, message = "Segundos deve ser maior ou igual 1 s")
        @Max(value = 10, message = "Segundos deve ser menor ou igual a 10 s")
        int segundos,

        @JsonProperty("erro_max")
        @DecimalMin(value = "0.000000000001", message = "Erro máximo deve ser maior que 0.000000000001")
        @NotNull(message = "Erro máximo não pode ser nulo")
        Double erroMax
) {

    public WaveCreateDTO(Double frequencia, Double comprimentoOnda, int segundos, Double erroMax) {
        this.frequencia = frequencia;
        this.comprimentoOnda = comprimentoOnda;
        this.segundos = segundos;
        this.erroMax = erroMax;
    }
}
