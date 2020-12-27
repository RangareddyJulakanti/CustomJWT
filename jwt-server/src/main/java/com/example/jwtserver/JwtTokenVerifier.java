package com.example.jwtserver;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    @Value("${secret}")
    private String secret;

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain) throws ServletException, IOException {

        String token= httpServletRequest.getHeader("token");
        Algorithm algorithm = Algorithm.HMAC256(((String) (secret)));
        try {
            // Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier =
                JWT.require(algorithm).withIssuer("WellsFargo").build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String roles = (String) jwt.getClaims().get("headers").asMap().get("roles");
            System.out.println(roles);
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            throw new RuntimeException(exception);
        }
    }

}
