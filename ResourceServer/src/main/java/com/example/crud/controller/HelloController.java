package com.example.crud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/hello")
public class HelloController {

    @GetMapping
    public Map<String,Object> hello(JwtAuthenticationToken auth) {
        var auths = auth.getAuthorities().stream().map(a -> a.getAuthority()).toList();
        return Map.of(
                "message", "Hello from Resource Server",
                "authorities", auths,
                "claims_scope", auth.getToken().getClaim("scope")
        );
    }


}

