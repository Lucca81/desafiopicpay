package com.desafiopicpay.desafiobackendpicpay.service;

import com.desafiopicpay.desafiobackendpicpay.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private WebClient webClient;


    public void sendNotification(User user, String message){
        String email = user.getEmail();

        Map<String, String> requestBody = Map.of(
                "email", email,
                "message", message
        );
        webClient.post()
                .uri("NOTIFICATION_URL")
                .bodyValue(requestBody)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    if (response.getStatusCode() == HttpStatus.OK) {
                        System.out.println("Notificação enviada com sucesso para " + email);
                    } else {
                        System.err.println("Falha ao enviar notificação para " + email);
                    }
                    return response;
                })
                .block();

    }
}
