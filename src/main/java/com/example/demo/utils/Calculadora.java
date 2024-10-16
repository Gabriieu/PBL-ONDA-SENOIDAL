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
}
