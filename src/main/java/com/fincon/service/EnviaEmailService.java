package com.fincon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;

import com.fincon.model.Email;

@Service
public class EnviaEmailService {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public String enviaEmail(Email pEmail) throws Exception {
        String requestBody = objectMapper.writeValueAsString(pEmail);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://envio-email.onrender.com/api/envia-email"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Async
    public String enviaEmailAnexo(Email pEmail) throws Exception {
        String requestBody = objectMapper.writeValueAsString(pEmail);
        HttpRequest request = HttpRequest.newBuilder()
                //.uri(new URI("https://envio-email.onrender.com/api/envia-email-anexo"))
                .uri(new URI("http://localhost:8080/api/envia-email-anexo"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}
