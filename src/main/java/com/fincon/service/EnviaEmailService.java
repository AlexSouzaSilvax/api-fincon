package com.fincon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;

import com.fincon.model.EmailSimples;

@Service
public class EnviaEmailService {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public String enviaEmailSimples(EmailSimples emailSimples) throws Exception {
        String requestBody = objectMapper.writeValueAsString(emailSimples);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://envio-email.onrender.com/api/envia-email"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}
