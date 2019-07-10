package com.bank.bank.Configs;

import com.bank.bank.Models.AccountCredentials;
import com.bank.bank.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private AccountCredentials creds;

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
         }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        System.out.println(creds);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), Collections.emptyList());
        System.out.println(usernamePasswordAuthenticationToken);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) {
        String jwtoken = Jwts.builder()
                .setSubject(auth.getName())
                .signWith(SignatureAlgorithm.HS512, "yes".getBytes())
                .compact();
        res.addHeader("Authorization", "Bearer " + jwtoken);
    }
}
