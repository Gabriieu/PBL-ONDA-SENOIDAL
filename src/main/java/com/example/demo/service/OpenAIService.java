package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.url.voice}")
    private String apiVoiceUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public String getCompletion(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Corpo da requisição
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        Map<String, String> teacherMessage = new HashMap<>();
        teacherMessage.put("role", "system");
        teacherMessage.put("content", "Você é um professor de física com doutorado no estudo de propagação de ondas. Você está respondendo a pergunta de um aluno sobre dados de uma onda que está salva em um banco de dados.");
        messages.add(teacherMessage);
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o"); // Utilize o modelo mais recente compatível
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

                    if (!choices.isEmpty()) {
                        Map<String, Object> firstChoice = choices.get(0);
                        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

                        if (message != null && message.containsKey("content")) {
                            return (String) message.get("content");
                        }
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Erro de cliente: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            System.err.println("Erro de servidor: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Erro ao obter resposta da OpenAI.";
    }


    public byte[] getTextToSpeech(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Corpo da requisição
        Map<String, Object> body = new HashMap<>();
        body.put("model", "tts-1"); // Modelo de texto para fala
        body.put("input", text); // O texto a ser convertido em áudio
        body.put("voice", "alloy"); // A voz a ser usada para a conversão

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            // Fazendo a requisição para a API de text-to-speech
            ResponseEntity<byte[]> response = restTemplate.exchange(apiVoiceUrl, HttpMethod.POST, request, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Retorna o byte array do áudio
                return response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Retorna null em caso de erro
    }

}

