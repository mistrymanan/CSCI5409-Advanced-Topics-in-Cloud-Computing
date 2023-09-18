package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TemperatureRequest implements RequestType {

    WebClient webClient;

    public TemperatureRequest() {
        webClient = WebClient
                .builder()
                .baseUrl("http://temperature-service:6001").build();
    }

    @Override
    public ResponseEntity<?> retrieve(UserInfoRequest userInfoRequest) {
        ResponseEntity<String> result = webClient.post()
                .uri("/user-info")
                .body(Mono.just(userInfoRequest), UserInfoRequest.class)
                .retrieve()
                .toEntity(String.class).block();
        System.out.println(result);
        return result;
    }
}
