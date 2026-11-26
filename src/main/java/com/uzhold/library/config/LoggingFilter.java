package com.uzhold.library.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println(">>> KIRDI:" + request.getMethod() + " " + request.getRequestURI());
        String secret = request.getHeader("X-Secret");
        if(!"salom123".equals(secret)){
            response.setStatus(401);
            response.getWriter().write("Secret is invalid");
            return;
        }
        chain.doFilter(request, response);
        System.out.println("<<<CHIQDI : status" + response.getStatus());
    }
}
