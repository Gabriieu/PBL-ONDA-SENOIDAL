package com.example.demo.controller;

import com.example.demo.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class OpenAIController {

    private final OpenAIService openAIService;

    @PostMapping("/text-to-speech")
    public ResponseEntity<byte[]> textToSpeech(@RequestBody String text) {
        var responseText = openAIService.getCompletion(text);
        byte[] audioData = openAIService.getTextToSpeech(responseText);

        if (audioData != null) {
            // Define os cabeçalhos de resposta para o arquivo de áudio
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("audio/mpeg")); // Define o tipo como MP3
            headers.setContentDisposition(ContentDisposition.inline().filename("audio.mp3").build()); // Para reprodução direta no navegador

            return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

