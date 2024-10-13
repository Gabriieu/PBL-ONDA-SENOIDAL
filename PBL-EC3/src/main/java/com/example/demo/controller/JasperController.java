package com.example.demo.controller;

import com.example.demo.entity.Wave;
import com.example.demo.service.JasperService;
import com.example.demo.service.WaveService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/report/print")
@RequiredArgsConstructor
public class JasperController {

    private final JasperService jasperService;
    private final WaveService waveService;

    @GetMapping
    public void getPDF(@RequestParam Long id, @RequestParam Double time, HttpServletResponse response) throws IOException {
        Wave wave = waveService.findById(id);

        if (time > wave.getDuracao()) {
            response.sendError(400, "O tempo deve ser menor ou igual a duração da onda");
            return;
        }

        // Multiplicando por 100 para trabalhar com inteiros
        int scaledTime = (int) Math.round(time * 100);

        // Verificando se o valor é múltiplo de 5 (0.05 * 100 = 5)
        if (scaledTime % 5 != 0) {
            response.sendError(400, "O tempo deve ser múltiplo de 0.05");
            return;
        }

        byte[] bytes = jasperService.exportPDF(id, time);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=report.pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

}
