package org.task.itms_db.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.task.itms_db.service.CustomUserDetailsService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService service;
    private final CustomUserDetailsService userDetailsService;


    public JwtTokenFilter(JwtService service, CustomUserDetailsService userDetailsService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException, ExpiredJwtException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                try {
                    String userEmail = service.extractUsername(jwt);
                    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                        if (service.isValidToken(jwt, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            authToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                } catch (ExpiredJwtException ex) {
                    response.setHeader(HttpHeaders.AUTHORIZATION, null);
                    response.sendError(401, "Токен не валідний");
                }
            }

            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            throw new AccessDeniedException("Немає доступу до цього ресурсу, треба пройти авторизацію");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/mail/**", request.getServletPath());
    }
}