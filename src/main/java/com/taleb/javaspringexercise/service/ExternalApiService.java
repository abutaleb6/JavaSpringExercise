package com.taleb.javaspringexercise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String callExternalApi() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://www.google.com", String.class);
        return response.getBody();
    }
}
