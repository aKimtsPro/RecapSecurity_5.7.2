package com.example.recapsecurity.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.recapsecurity.models.forms.LoginForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class SessionController {

    private final AuthenticationManager manager;

    public SessionController(AuthenticationManager manager) {

        this.manager = manager;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm form){

        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
        return JWT.create()
                .withExpiresAt( Instant.ofEpochMilli( System.currentTimeMillis() + 86400000 ) )
                .withSubject( auth.getName() )
                .withClaim("roles", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList() )
                .sign( Algorithm.HMAC512( "N9wEH4fCqgevCExRWdtDsy8X" ) );

    }

}
