package com.example.AuthorizationServer.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
public class AuthServerConfig {

    @Bean
    @Order
    SecurityFilterChain authorizationServer(HttpSecurity httpSecurity) throws Exception {

        var authorizationServerConfigure =OAuth2AuthorizationServerConfigurer.authorizationServer();
        var endpoints = authorizationServerConfigure.getEndpointsMatcher();

        httpSecurity
                .securityMatcher(endpoints)
                .authorizeHttpRequests(auth ->auth.anyRequest().authenticated())
                .csrf(csrf->csrf.ignoringRequestMatchers(endpoints))
                .with(authorizationServerConfigure,conf->{})
                .oauth2ResourceServer(o->o.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }

    @Bean
    RegisteredClientRepository registeredClientRepository() {
        RegisteredClient client= RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("demo-client")
                .clientSecret("{noop}demo-secret")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("artist.read")
                .scope("artist.write")
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30)).build())
                .build();
        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() throws Exception{
        var kpg= KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        var kp = kpg.generateKeyPair();
        var jwk = new RSAKey.Builder((RSAPublicKey) kp.getPublic())
                .privateKey((RSAPrivateKey) kp.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();

        var set = new JWKSet(jwk);
        return ((jwkSelector, securityContext) -> jwkSelector.select(set));
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings
                .builder()
                .issuer("http://localhost:9000")
                .build();
    }
}
