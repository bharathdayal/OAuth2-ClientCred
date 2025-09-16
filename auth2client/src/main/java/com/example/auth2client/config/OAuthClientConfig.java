package com.example.auth2client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;


@Configuration
@EnableWebSecurity
public class OAuthClientConfig {

    @Bean
    SecurityFilterChain clientSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/v1/call/**").permitAll()   // allow your test endpoint
                        .anyRequest().denyAll())
                .formLogin(f -> f.disable())                // no login page
                .httpBasic(b -> b.disable()) ;
        // no basic auth either
               // .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository registrations) {

        OAuth2AuthorizedClientService svc =
                new InMemoryOAuth2AuthorizedClientService(registrations);

        var provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(registrations, svc);
        manager.setAuthorizedClientProvider(provider);
        return manager;
    }

    /** Synthetic principal for client_credentials (no real user involved) */
    private Authentication clientPrincipal() {
        return new AnonymousAuthenticationToken("cc", "system",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }

    /** RestClient that auto-attaches Bearer tokens */
    @Bean(name = "oauthRestClient")
    public RestClient restClient(OAuth2AuthorizedClientManager manager) {

        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    System.out.println(">>> INTERCEPTOR HIT for " + request.getURI());  // must print
                    var authorizeRequest = OAuth2AuthorizeRequest
                            .withClientRegistrationId("demo-client")
                            .principal(clientPrincipal())
                            .build();

                    var client = manager.authorize(authorizeRequest);

                    if (client == null || client.getAccessToken() == null) {
                        throw new IllegalStateException("Failed to obtain access token for 'demo-client'");
                    }
                    var token = client.getAccessToken().getTokenValue();
                    System.out.println("CLIENT sends Bearer: " + token.substring(0, 20) + "...");

                    request.getHeaders().setBearerAuth(token);
                    return execution.execute(request, body);
                })
                .build();
    }


}

