package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 	Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		System.out.println("==============================================================================");
		System.out.println("A aplicação foi iniciada...");
		System.out.println("Faça o download da planilha acessando http://localhost:8080/dashboard/download");
		System.out.println("Certifique-se de habilitar as macros para visualizar os dados.");
		System.out.println("==============================================================================");
	}

}
