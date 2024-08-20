package com.fincon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        System.out.println("\n\n<--------------------------------->\n\n");
        System.out.println("Enviando e-mail simples...\n");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Enviado com sucesso!");
        System.out.println("\n\n<--------------------------------->\n\n");
        return response.body();
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
        System.out.println("\n\n<--------------------------------->\n\n");
        System.out.println("Enviando e-mail com anexo...\n");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Enviado com sucesso!");
        System.out.println("\n\n<--------------------------------->\n\n");
        return response.body();
    }

}
