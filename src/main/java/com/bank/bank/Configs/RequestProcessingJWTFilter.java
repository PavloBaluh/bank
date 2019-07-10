package com.bank.bank.Configs;

import com.bank.bank.Service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class RequestProcessingJWTFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey("yes".getBytes())
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();
            authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
