package com.example.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http
                .csrf(c->c.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/v1/hello").hasAuthority("SCOPE_artist.read")
                        .requestMatchers("/v1/artist/**").hasAuthority("SCOPE_artist.write")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(o->o.jwt(
                        j->j.jwtAuthenticationConverter(jwtAuthConverter())
                ));// <-- REQUIRED
        return http.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthConverter() {
        var scopes = new JwtGrantedAuthoritiesConverter();
        scopes.setAuthorityPrefix("SCOPE_");
        scopes.setAuthoritiesClaimName("scope"); // Spring AS uses 'scope' for client_credentials
        var conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(scopes);
        return conv;
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(c->c.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/v1/artist/**").authenticated()
                        .anyRequest().permitAll())


                .httpBasic(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        System.out.println(encoder.encode("super-secret"));
        UserDetails userDetails = User.withUsername("svc8080")
                .password(encoder.encode("super-secret"))
                .roles("CALLER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain authorizeServerApi(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(c->c.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/artist/**").hasAnyAuthority("SCOPE_artist.read")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(o->o.jwt(
                        j->j.jwtAuthenticationConverter(jwtAuthConverter())
                ));
        return httpSecurity.build();

    }*/


}
