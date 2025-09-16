# OAuth2 Demo with Spring Boot

This project demonstrates a simple OAuth2 flow using Spring Authorization Server (AS), Spring Boot Resource Server (RS), and an OAuth2 Client Application.

## 📌 Architecture Overview

```
+--------------------+        +--------------------+        +--------------------+
|  OAuth2 Client     | -----> | Authorization      | -----> | Resource Server    |
|  (Your App)        |        | Server (AS)        |        | (API)              |
| Requests token     |        | Issues JWT         |        | Validates JWT      |
+--------------------+        +--------------------+        +--------------------+
```

- **Authorization Server (AS)** → Issues JWTs and Refresh Tokens  
- **Resource Server (RS)** → Protects APIs and validates JWTs  
- **OAuth2 Client** → Requests tokens and uses them to access RS

---

## 1. Authorization Server (AS)

**Dependencies**
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-authorization-server'
}
```

**application.yml**
```yaml
server:
  port: 9000

spring:
  security:
    oauth2:
      authorizationserver:
        client:
          demo-client:
            registration:
              client-id: demo-client
              client-secret: "{noop}demo-secret"
              authorization-grant-types: client_credentials
              scopes: artist.read
```

**Sample Config**
```java
@Configuration
public class AuthorizationServerConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("demo-client")
            .clientSecret("{noop}demo-secret")
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("artist.read")
            .build();
        return new InMemoryRegisteredClientRepository(client);
    }
}
```

---

## 2. Resource Server (RS)

**Dependencies**
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
}
```

**application.yml**
```yaml
server:
  port: 8083

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9000
```

**Sample Config**
```java
@Configuration
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/v1/artist").hasAuthority("SCOPE_artist.read")
              .anyRequest().authenticated())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
}
```

**Sample Controller**
```java
@RestController
@RequestMapping("/v1")
class ArtistController {
    @GetMapping("/artist")
    public String getArtist() {
        return "🎵 Artist details fetched securely!";
    }
}
```

---

## 3. OAuth2 Client (Your App)

**Dependencies**
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
}
```

**application.yml**
```yaml
server:
  port: 8081

spring:
  security:
    oauth2:
      client:
        registration:
          demo-client:
            client-id: demo-client
            client-secret: demo-secret
            authorization-grant-type: client_credentials
            scope: artist.read
        provider:
          local-issuer:
            issuer-uri: http://localhost:9000
```

**Sample Client Code**
```java
@RestController
@RequestMapping("/client")
public class ClientController {

    private final RestClient restClient;

    public ClientController(RestClient.Builder builder, OAuth2AuthorizedClientManager manager) {
        this.restClient = builder
            .requestInterceptor(new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager))
            .baseUrl("http://localhost:8083/v1")
            .build();
    }

    @GetMapping("/artist")
    public String callResource() {
        return restClient.get()
            .uri("/artist")
            .retrieve()
            .body(String.class);
    }
}
```

---

## ▶️ How to Run

**Start Authorization Server**
```sh
./gradlew :auth-server:bootRun
```
Runs at [http://localhost:9000](http://localhost:9000)

**Start Resource Server**
```sh
./gradlew :resource-server:bootRun
```
Runs at [http://localhost:8083/v1/artist](http://localhost:8083/v1/artist)

**Start Client**
```sh
./gradlew :client-app:bootRun
```
Access Client at [http://localhost:8081/client/artist](http://localhost:8081/client/artist)

---

## 🔒 Example Flow

1. Client fetches token from AS  
   `POST http://localhost:9000/oauth2/token`
2. Client calls RS  
   `GET http://localhost:8083/v1/artist` with `Authorization: Bearer <token>`
3. RS validates JWT locally (via AS public keys)
4. API returns secured data 🎉

---

## 🛠️ Notes

- Replace demo-client/demo-secret with your own values in production.
- For real-world setups, use Keycloak or Okta instead of the embedded AS.
- Use HTTPS, database-backed Registered Clients, and refresh tokens where applicable.
