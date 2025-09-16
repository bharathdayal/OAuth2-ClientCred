package com.example.auth2client.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;

@RestController
@RequestMapping("/v1/call")
public class CallerController {
    private final RestClient restClient;

    public CallerController(@Qualifier("oauthRestClient")  RestClient restClient) {
        this.restClient = restClient;
        System.out.println(">>> INJECTED RestClient bean: oauthRestClient");
    }


    @GetMapping
    public ResponseEntity<String> call() {
        String json = restClient.get()
                .uri("http://localhost:8083/v1/hello")
                .retrieve()
                .body(String.class);              // get raw JSON string
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);                      // return it as-is
    }

    @GetMapping("/artist")
    public ResponseEntity<String> callartist() {
        String json = restClient.get()
                .uri("http://localhost:8083/v1/artist")
                .retrieve()
                .body(String.class);              // get raw JSON string
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);                      // return it as-is
    }


   /* @GetMapping("/artist")
    public String getArtists() {
        try {
            return restClient.get()
                    .uri("http://localhost:8083/v1/artist")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException ex) {
            return errorJson(ex);
        }
    }

    private String errorJson(RestClientResponseException ex) {
        // return a simple JSON error payload; adjust to your taste
        return String.format("{\"status\":%d,\"error\":\"%s\",\"body\":%s}",
                ex.getRawStatusCode(),
                ex.getStatusText(),
                jsonSafe(ex.getResponseBodyAsString()));
    }

    private String jsonSafe(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n") + "\"";
    } */
}
