package com.fincon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincon.model.Email;

@Service
public class EnviaEmailService {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL = System.getenv("FINCON_ENVIO_EMAIL_URL");

    @Async
    public String enviaEmail(Email pEmail) throws Exception {
        String uri = URL + "api/envia-email";
        String requestBody = objectMapper.writeValueAsString(pEmail);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new Exception("Erro ao enviar email. Código de status: " + response.statusCode());
            }
        } catch (java.net.http.HttpTimeoutException e) {
            throw new Exception("Erro de timeout ao enviar email.");
        } catch (Exception e) {
            throw new Exception("Erro desconhecido ao enviar email.");
        }
    }

    @Async
    public String enviaEmailAnexo(Email pEmail) throws Exception {
        String uri = URL + "api/envia-email-anexo";
        String requestBody = objectMapper.writeValueAsString(pEmail);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
