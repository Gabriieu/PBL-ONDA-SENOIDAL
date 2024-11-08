package com.example.demo.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/dashboard")
public class ExcelController {

    private static final String PLANILHA_PATH = "excel/Dashboard.xlsm"; // Caminho para sua planilha


    @GetMapping("/download")
    public ResponseEntity<Resource> baixarPlanilha() throws IOException {
        Resource resource = new ClassPathResource(PLANILHA_PATH);
        System.out.println(PLANILHA_PATH);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Lê o arquivo com macros como bytes
        InputStream inputStream = resource.getInputStream();
        byte[] bytes = inputStream.readAllBytes();  // Lê o arquivo como um array de bytes

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Dashboard.xlsm\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(new ByteArrayResource(bytes)); // Retorna o arquivo como byte array
    }
}
