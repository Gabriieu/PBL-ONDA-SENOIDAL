package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

// Serviço que gera relatórios Jasper
@Service
// Gera um construtor com todas as dependências
@RequiredArgsConstructor
public class JasperService {

    // Diretório onde o arquivo Jasper está localizados
    private static final String JASPER_PATH = "classpath:jasper/";
    private static final String JASPER_WAVE_PDF_PREFIX = "ondas";
    private static final String JASPER_USER_WAVES_PDF_PREFIX = "ondas-por-usuario";
    private static final String JASPER_SUFFIX = ".jasper";

    // Conexão com o banco de dados
    private final Connection connection;

    // Parâmetros do relatório Jasper
    private Map<String, Object> params = new HashMap<>();

    // Adiciona um parâmetro ao relatório Jasper
    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    // Gerar um relatório Jasper em PDF
    public byte[] exportWavePDF(Long id, Double time) {
        byte[] bytes = null;

        try {
            File file = ResourceUtils.getFile(JASPER_PATH.concat(JASPER_WAVE_PDF_PREFIX).concat(JASPER_SUFFIX));
            addParam("PARAM_ONDA_ID", id);
            addParam("PARAM_TEMPO", time);

            JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);

            bytes = JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo Jasper não encontrado: " + e.getMessage(), e);
        } catch (JRException e) {
            throw new RuntimeException("Erro ao preencher ou exportar o relatório: " + e.getMessage(), e);
        }

        // Retorna o relatório em um array de bytes
        return bytes;
    }

    public byte[] exportUserWavesPDF(Long id) {
        byte[] bytes = null;

        try {
            File file = ResourceUtils.getFile(JASPER_PATH.concat(JASPER_USER_WAVES_PDF_PREFIX).concat(JASPER_SUFFIX));
            addParam("PARAM_USUARIO_ID", id);

            JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);

            bytes = JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo Jasper não encontrado: " + e.getMessage(), e);
        } catch (JRException e) {
            throw new RuntimeException("Erro ao preencher ou exportar o relatório: " + e.getMessage(), e);
        }

        // Retorna o relatório em um array de bytes
        return bytes;
    }
}
