package com.fincon.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fincon.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    // Lista de rotas públicas, onde o token não será validado
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/api/auth/register",
            "/api/auth/login",
            "/api/user/esqueci-senha",
            "/render/api/get");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Verifica se a requisição está em uma das rotas públicas
        if (isPublicRoute(request.getRequestURI())) {
            filterChain.doFilter(request, response); // Segue o filtro sem validar o token
            return;
        }

        var token = this.recoverToken(request);

        if (token != null) {
            try {
                var username = tokenService.validateToken(token);
                UserDetails user = userRepository.findByUsername(username);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("msg", "Token inválido ou expirado. Faça login novamente.");
                response.getWriter().write("Token inválido ou expirado. Faça login novamente.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPublicRoute(String requestUri) {
        return PUBLIC_URLS.stream().anyMatch(requestUri::matches);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }

}
