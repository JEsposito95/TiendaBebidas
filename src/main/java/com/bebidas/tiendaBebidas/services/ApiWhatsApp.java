package com.bebidas.tiendaBebidas.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiWhatsApp {
    @Value("${meta.whatsapp.api.url}")
    private String apiUrl;

    @Value("${meta.whatsapp.phone.id}")
    private String phoneId;

    @Value("${meta.whatsapp.token}")
    private String token;

    public boolean enviarMensaje(String mensaje, String numeroDestino) {
        RestTemplate restTemplate = new RestTemplate();

        // Construcción del endpoint
        String url = apiUrl + "/" + phoneId + "/messages";

        // Configuración de encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        // Cuerpo de la petición
        String body = """
            {
                "messaging_product": "whatsapp",
                "to": "%s",
                "type": "text",
                "text": {"body": "%s"}
            }
            """.formatted(numeroDestino, mensaje);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
