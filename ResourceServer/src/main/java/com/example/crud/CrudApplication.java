package com.example.crud;

import com.example.crud.designpattern.SingletonLog;
import com.nimbusds.jose.Header;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.sql.ast.tree.from.TableJoin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.swing.text.html.Option;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.nio.file.OpenOption;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(CrudApplication.class, args);



















	}

}
