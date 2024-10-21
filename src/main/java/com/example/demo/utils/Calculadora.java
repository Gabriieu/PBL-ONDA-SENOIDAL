package com.example.demo.utils;

import com.example.demo.entity.Ponto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Classe que realiza os cálculos
@Component
public class Calculadora {

    public List<Ponto> calculaY(Double frequencia, Double lambda, int duracao) {
        List<Ponto> response = new ArrayList<>();
        double x = 0;

        while (x <= lambda + 0.01) {
            double milisegundo = 0.00;
            while (milisegundo < duracao + 0.005) {
                Ponto ponto = new Ponto();
                var PI = Math.PI;
                Double y = Math.sin(2 * PI * (frequencia * milisegundo - x / lambda));
                ponto.setX(x);
                ponto.setT(milisegundo);
                ponto.setY(y);
                response.add(ponto);
                milisegundo += 0.05;
            }
            x += lambda / 20;
            System.out.println(x);
        }
        return response;
    }

    //taylor
    public double taylor(double teta, double erroMax) {
        double PI = Math.PI;
        int k = 0;  // Iterações
        double r = 1;  // Resto
        double seno = 0;  // Valor final do seno
        boolean negativo = false;  // Indica se o termo na série é negativo

        // Ajusta o valor de teta para estar no intervalo [-pi, pi]
        while (teta >= PI || teta <= -PI) {
            if (teta >= PI) {
                teta -= PI;
            }
            if (teta <= -PI) {
                teta += PI;
            }
        }

        // Calcula a Série de Taylor até que o erro seja menor que o erroMax
        while (r > erroMax) {
            if (k % 2 == 0) {
                seno += 0 * Math.pow(teta, k);
            } else if (k % 2 != 0.0 && !negativo) {  // Verifica se o termo é ímpar e positivo
                seno += (1.0 / fatorial(k)) * Math.pow(teta, k);
                negativo = true;  // Próximo termo será negativo
            } else if (k % 2 != 0.0 && negativo) {  // Verifica se o termo é ímpar e negativo
                seno -= (1.0 / fatorial(k)) * Math.pow(teta, k);
                negativo = false;  // Próximo termo será positivo
            }

            // Calcula o valor do resto
            r = Math.pow(Math.abs(teta), k + 1) / fatorial(k + 1);
            k++;
        }

        return seno;
    }

    // Função para calcular o fatorial
    private static double fatorial(int k) {
        if (k == 0) {
            return 1;
        }
        return k * fatorial(k - 1);
    }
}
