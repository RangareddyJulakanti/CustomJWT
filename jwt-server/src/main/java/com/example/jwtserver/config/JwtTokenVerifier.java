package com.example.jwtserver.config;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtTokenVerifier extends GenericFilterBean {
    @Value("${secret}")
    private String secret;



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        Authentication authentication=getAuthentication(httpServletRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter ( request, response );
        SecurityContextHolder.clearContext ();
    }
    private Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        String token= httpServletRequest.getHeader("token");
        Algorithm algorithm = Algorithm.HMAC256(((String) (secret)));
        try {
            // Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier =
                JWT.require(algorithm).withIssuer("WellsFargo").build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String roleString = (String) jwt.getClaims().get("headers").asMap().get("roles");
            String [] roles=roleString.split(",");

            System.out.println(roles);
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
            return new PreAuthenticatedAuthenticationToken( jwt.getClaims().get("headers").asMap().get("username"), "", authorities );
        }catch (TokenExpiredException ex){
            throw  ex;
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            throw new RuntimeException(exception);
        }
    }
}
