package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Wave;
import com.example.demo.exception.exceptions.PDFRequestErrorException;
import com.example.demo.jwt.JwtUserDetails;
import com.example.demo.service.JasperService;
import com.example.demo.service.UserService;
import com.example.demo.service.WaveService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
// Define a classe como um controller REST
@RequestMapping("/report/print")
// Define a rota base para a API
@RequiredArgsConstructor
// Gera um construtor com todas as dependências
public class JasperController {

    // Injeção de dependências
    private final JasperService jasperService;
    private final WaveService waveService;
    private final UserService userService;

    @GetMapping
    public void getWavePDF(@RequestParam Long id, @RequestParam Double time, HttpServletResponse response) throws IOException {
        // Buscando a onda pelo id
        Wave wave = waveService.findById(id);

        // O tempo solicitado não pode ser maior que a duração da onda
        if (time > wave.getDuracao()) {
            throw new PDFRequestErrorException("O tempo deve ser menor ou igual a duração da onda");
        }

        // O tempo solicitado não pode ser menor que 0
        if(time < 0){
            throw new PDFRequestErrorException("O tempo deve ser maior ou igual a 0");
        }

        // Multiplicando por 100 para trabalhar com inteiros
        int scaledTime = (int) Math.round(time * 100);

        // Verificando se o valor é múltiplo de 5 (0.05 * 100 = 5)
        if (scaledTime % 5 != 0) {
            throw new PDFRequestErrorException("O tempo deve ser múltiplo de 0.05");
        }

        // Exportando o relatório em PDF
        byte[] bytes = jasperService.exportWavePDF(id, time);

        // Configurando a resposta
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=report.pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

    @GetMapping("/user")
    public void getUserWaves(@AuthenticationPrincipal JwtUserDetails userDetails, HttpServletResponse response) throws IOException {
        // Buscando o usuário logado pelo id
        User user = userService.findById(userDetails.getId());
        Long wavesCount = waveService.countByUserId(user.getId());

        // O usuário deve ter pelo menos uma onda
        if (wavesCount == 0) {
            throw new PDFRequestErrorException("O usuário não possui simulações");
        }

        // Exportando o relatório em PDF
        byte[] bytes = jasperService.exportUserWavesPDF(user.getId());

        // Configurando a resposta
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=report.pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

}
