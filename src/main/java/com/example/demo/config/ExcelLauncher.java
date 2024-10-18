package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ExcelLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // ClassPathResource para obter o arquivo do classpath
        File file = new ClassPathResource("excel/Dashboard.xlsm").getFile();
        String filePath = file.getAbsolutePath();

        // Comando para abrir o Excel com a planilha
        Runtime.getRuntime().exec("cmd /c start excel \"" + filePath + "\"");

        System.out.println("Planilha Excel iniciada");
    }
}


