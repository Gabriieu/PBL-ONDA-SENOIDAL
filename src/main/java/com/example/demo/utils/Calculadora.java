package com.example.demo.utils;

import com.example.demo.entity.Ponto;
import com.example.demo.entity.Wave;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Classe que realiza os cálculos
@Component
public class Calculadora {

    public List<Ponto> calculaY(Wave wave) {
        List<Ponto> response = new ArrayList<>();
        double x = 0;

        while (x <= wave.getComprimentoOnda() + 0.01) {
            double milisegundo = 0.00;
            while (milisegundo < wave.getDuracao() + 0.005) {
                Ponto ponto = new Ponto();
                var PI = Math.PI;
                Double y = taylor(2 * PI * (wave.getFrequencia() * milisegundo - x / wave.getComprimentoOnda()), wave.getErroMax());
                ponto.setX(x);
                ponto.setT(milisegundo);
                ponto.setY(y);
                response.add(ponto);
                milisegundo += 0.05;
            }
            x += wave.getComprimentoOnda() / 40;
        }
        return response;
    }

    //taylor
    public double taylor(double teta, double erroMax) {
        double PI = Math.PI;
        int k = 0;  // Iterações
        double r = 1;  // Resto
        double seno = 0;  // Valor final do seno

        // Ajusta o valor de teta para estar no intervalo [-pi, pi]
        teta = teta % (2 * PI);
        if (teta > PI) teta -= 2 * PI;
        if (teta < -PI) teta += 2 * PI;

        // Calcula a Série de Taylor até que o erro seja menor que o erroMax
        while (r > erroMax) {
            // Termo da série de Taylor
            double termo = Math.pow(teta, 2 * k + 1) / fatorial(2 * k + 1);
            if (k % 2 == 0) {  // Adiciona quando k é par
                seno += termo;
            } else {  // Subtrai quando k é ímpar
                seno -= termo;
            }

            // Calcula o valor do resto
            r = Math.abs(termo);  // Resto é o termo atual
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
