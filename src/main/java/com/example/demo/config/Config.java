package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.sql.DataSource;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.SQLException;


// Classe de configuração para criar um bean de conexão com o banco de dados
@Configuration
public class Config {

    // Método que cria um bean de conexão com o banco de dados para utilização do JasperReports
    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        // Cria um TrustManager que não valida os certificados
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // Inicializa um contexto SSL que usa o TrustManager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());

        // Configura o SSL para o HttpsURLConnection
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }
}
