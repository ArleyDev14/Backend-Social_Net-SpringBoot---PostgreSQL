package com.social_net.social_net.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.social_net.social_net.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    String requestPath = request.getServletPath();

    // Ignorar rutas públicas (registro, login, Swagger y documentación)
    if (requestPath.startsWith("/api/auth/register") ||
        requestPath.startsWith("/api/auth/login") ||
        requestPath.startsWith("/swagger-ui") || 
        requestPath.startsWith("/swagger-ui.html") ||
        requestPath.startsWith("/v3/api-docs") ||
        requestPath.startsWith("/v2/api-docs") ||
        requestPath.startsWith("/mi-documentacion") ||
        requestPath.startsWith("/mi-api") ||
        requestPath.startsWith("/webjars")) {
        filterChain.doFilter(request, response);
        return;
    }

    final String token = getTokenFromRequest(request);
    final String username;

    if (token == null) {
        filterChain.doFilter(request, response);
        return;
    }

    username = jwtService.getUsernameFromToken(token);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
}
    

    private String getTokenFromRequest(HttpServletRequest request){
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
