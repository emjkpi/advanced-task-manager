package com.example.home_accountant.filter;

import com.example.home_accountant.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.home_accountant.security.JwtAuthenticationToken;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null && JwtUtil.validateToken(token)) {
            String email = JwtUtil.getEmailFromToken(token);

            SecurityContextHolder.getContext().setAuthentication(
                    new JwtAuthenticationToken(email, new WebAuthenticationDetailsSource().buildDetails(request))
            );
        }

        filterChain.doFilter(request, response);
    }
}
