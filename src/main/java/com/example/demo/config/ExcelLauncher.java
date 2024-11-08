package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExcelLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("=========================================");
        System.out.println("A aplicação foi iniciada...");
        System.out.println("Faça o download da planilha acessando http://localhost:8080/dashboard/download");
        System.out.println("Lembre-se de habilitar as macros para visualizar os dados.");
        System.out.println("=========================================");
    }
}


