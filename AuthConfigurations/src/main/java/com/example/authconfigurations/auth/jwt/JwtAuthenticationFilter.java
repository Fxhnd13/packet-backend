package com.example.authconfigurations.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @implNote Esta clase es responsable de interceptar las peticiones que llegan al servidor y verificar si el token es válido
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    /**
     * @implNote Este método es el encargado de interceptar las peticiones que llegan al servidor y verificar si el token es válido
     * @param request Es la petición que llega al servidor
     * @param response Es la respuesta que se envía al cliente
     * @param filterChain Es la cadena de filtros que se ejecutan en el servidor
     * @throws ServletException Es la excepción que se lanza cuando ocurre un error en el servidor
     * @throws IOException Es la excepción que se lanza cuando ocurre un error en la entrada o salida de datos
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String jwt = request.getHeader("Authorization");
        if(jwt == null){
            filterChain.doFilter(request, response);
            return;
        }

        final Claims claims = jwtService.extractAllClaims(jwt);
        if(claims != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = User.builder()
                    .username((String) claims.get("username"))
                    .password("")
                    .authorities(jwtService.extractAuthorities(claims))
                    .build();
            if(jwtService.istokenValid(jwt)){
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
            filterChain.doFilter(request, response);
        }
    }
}
