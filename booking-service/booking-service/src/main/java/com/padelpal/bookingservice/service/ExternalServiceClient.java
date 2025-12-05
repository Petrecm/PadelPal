package com.padelpal.bookingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalServiceClient {

    private final WebClient webClientUser;
    private final WebClient webClientCourt;

    public ExternalServiceClient(
            @Value("${user.service.url}") String userUrl,
            @Value("${court.service.url}") String courtUrl
    ) {
        this.webClientUser = WebClient.create(userUrl);
        this.webClientCourt = WebClient.create(courtUrl);
    }

    public boolean userExists(Long userId) {
        try {
            webClientUser.get().uri("/" + userId).retrieve().bodyToMono(String.class).block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean courtExists(Long courtId) {
        try {
            webClientCourt.get().uri("/" + courtId).retrieve().bodyToMono(String.class).block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
