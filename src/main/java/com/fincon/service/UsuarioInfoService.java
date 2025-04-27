package com.fincon.service;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fincon.dto.UsuarioLocalizacaoDTO;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UsuarioInfoService {

    public String getIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    private static final String URL = "http://ip-api.com/json/";

    public static UsuarioLocalizacaoDTO buscarLocalizacao(String ip) {
        ip = "45.187.226.138";
        RestTemplate restTemplate = new RestTemplate();
        String urlFinal = URL + ip;
        Map<String, Object> resposta = restTemplate.getForObject(urlFinal, Map.class);

        if (resposta != null && "success".equals(resposta.get("status"))) {
            String latitude = resposta.get("lat").toString();
            String longitude = resposta.get("lon").toString();
            return new UsuarioLocalizacaoDTO(ip, latitude, longitude, gerarLinkGoogleMaps(latitude, longitude));
        }
        return null;
    }

    public static String gerarLinkGoogleMaps(String latitude, String longitude) {
        return "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
    }
}