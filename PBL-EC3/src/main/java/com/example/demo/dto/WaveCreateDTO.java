package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record WaveCreateDTO(
        @DecimalMin(value = "0.009", message = "Frequência deve ser maior que 0.009")
        //@DecimalMax(value = "10", message = "Frequência deve ser menor ou igual a 10")
        Double frequencia,

        @JsonProperty("comprimento_onda")
        @DecimalMin(value = "0.20", message = "Comprimento de onda deve ser maior que 0.2")
        //@DecimalMax(value = "2.00", message = "Comprimento de onda não pode ser maior que 2.0")
        Double comprimentoOnda,

        @Min(value = 1, message = "Segundos deve ser maior ou igual 1")
        @Max(value = 10, message = "Segundos deve ser menor ou igual a 10")
        int segundos,

        @JsonProperty("erro_max")
        @DecimalMin(value = "0.00000000001", message = "Erro máximo deve ser maior que 0.00000000001")
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
