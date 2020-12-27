package com.example.jwtclient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import com.auth0.jwt.impl.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenGenController {

  @GetMapping("/")
  public ResponseEntity generateToken(@RequestHeader Map<String, Object> headers)
      throws InterruptedException {
    // String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    Object secret = headers.remove("secret");
    Object userName = headers.remove("username");
    if (secret == null || userName == null) {
      throw new RuntimeException("secret/username should not be empty");
    }

    Algorithm algorithm = Algorithm.HMAC256(((String) (secret)));

    String token =
        JWT.create()
            .withIssuer("WellsFargo")
            .withClaim("headers", headers)
            .withSubject((String) userName)
            .withJWTId(UUID.randomUUID().toString())
            .withIssuedAt(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .withExpiresAt(
                Date.from(
                    LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .sign(algorithm);
    Thread.sleep(2000);

    return ResponseEntity.status(HttpStatus.CREATED).header("token", token).build();
  }
}
