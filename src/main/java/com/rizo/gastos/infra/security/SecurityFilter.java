package com.rizo.gastos.infra.security;

import com.auth0.jwt.JWT;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String login = tokenService.validateToken(token);
            authenticateUser(login, token, response, filterChain, request);
            log.info("Token verificado com sucesso: " + token);
        } catch (Exception ex) {
            log.error("Token validation error", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }

    private void authenticateUser(String login, String token, HttpServletResponse response, FilterChain filterChain, HttpServletRequest request) throws IOException, ServletException {
        if (login != null) {
            List<Usuario> users = usuarioRepository.findByEmail(login);
            if (users.isEmpty()) {
                log.warn("No user found for email: {}", login);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return; // Stop further filter execution and return the response
            }
            Usuario usuario = users.get(0);

            // Ensure role is not null or empty
            if (usuario.getRole() == null || usuario.getRole().trim().isEmpty()) {
                log.error("User role is null or empty for user: {}", login);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User role not defined");
                return;
            }

            var authorities = Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole()));
            var authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Renovar o token se estiver perto de expirar
            if (tokenExpiringSoon(token)) {
                String newToken = tokenService.generateToken(usuario);
                response.setHeader("Authorization", "Bearer " + newToken);
            }

            filterChain.doFilter(request, response);
        }
    }

    private boolean tokenExpiringSoon(String token) {
        Instant expiration = JWT.decode(token).getExpiresAt().toInstant();
        Instant currentTime = Instant.now();
        Instant fifteenMinutesLater = currentTime.plusSeconds(15 * 60); // 15 minutos em segundos
        return expiration.isBefore(fifteenMinutesLater);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No or incorrect Authorization header format");
            return null;
        }
        return authHeader.substring(7); // Skip "Bearer " part
    }
}
