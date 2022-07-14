package com.example.recapsecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if( authHeader != null ){
            String token = authHeader.replace("Bearer ", "");

            try{
                DecodedJWT decodedJWT = JWT.require( Algorithm.HMAC512("N9wEH4fCqgevCExRWdtDsy8X") )
                        .build().verify(token);

                if( decodedJWT.getExpiresAt().toInstant().isAfter( Instant.now() ) ){
                    UserDetails details = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
                    Authentication auth = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            catch (JWTVerificationException | UsernameNotFoundException ex){
                log.error("auth via JWT failed", ex);
            }
        }
        filterChain.doFilter(request, response);

    }
}
