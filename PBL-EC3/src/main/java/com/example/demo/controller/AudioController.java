package com.example.demo.controller;

import com.example.demo.entity.Wave;
import com.example.demo.service.WaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/audio")
@RequiredArgsConstructor
public class AudioController {

    private final WaveService waveService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generateWav(@PathVariable Long id) {
        Wave wave = waveService.findById(id);

        float sampleRate = 44100f; // Taxa de amostragem
        int duration = wave.getDuracao(); // Duração em segundos
        byte[] soundData = generateSineWave(sampleRate, duration, wave.getFrequencia()); // 440Hz (Lá)

        // Criar arquivo .wav em memória
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
            AudioInputStream audioInputStream = new AudioInputStream(
                    new ByteArrayInputStream(soundData),
                    format,
                    soundData.length / format.getFrameSize()
            );

            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, byteArrayOutputStream);

            byte[] wavData = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulacao_id_" + wave.getId() +".wav");
            return new ResponseEntity<>(wavData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] generateSineWave(float sampleRate, int duration, double frequency) {
        int totalSamples = (int) (duration * sampleRate);
        byte[] soundData = new byte[totalSamples * 2]; // 16 bits = 2 bytes

        for (int i = 0; i < totalSamples; i++) {
            double angle = 2.0 * Math.PI * i / (sampleRate / frequency);
            short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
            soundData[i * 2] = (byte) (sample & 0xFF);
            soundData[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
        }
        return soundData;
    }
}
