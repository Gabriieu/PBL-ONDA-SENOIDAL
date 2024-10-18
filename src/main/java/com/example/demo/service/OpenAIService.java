package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.url.voice}")
    private String apiVoiceUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCompletion(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Corpo da requisição
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini"); // Utilize o modelo mais recente compatível
        body.put("messages", new Object[]{new HashMap<String, String>() {{
            put("role", "user");
            put("content", prompt);
        }}});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("choices")) {
                    // "choices" é uma lista de mapas
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

                    // Pegue o primeiro item da lista "choices"
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

                    // Retorna o conteúdo da resposta
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Erro ao obter resposta da OpenAI.";
    }

    public byte[] getTextToSpeech(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); // Insira sua chave de API aqui
        headers.set("Content-Type", "application/json");

        // Corpo da requisição
        Map<String, Object> body = new HashMap<>();
        body.put("model", "tts-1"); // O texto a ser convertido em áudio
        body.put("input", text);
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

