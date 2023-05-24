package com.example.authconfigurations.auth.jwt;

import com.example.authconfigurations.auth.models.RedisManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @implNote Esta clase es responsable de generar y validar los tokens
 */
@Service
public class JwtService {

    /**
     * @implNote Esta es la llave secreta que se utiliza para generar los tokens
     */
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566D597033733676397924";
    /**
     * @implNote Esta es la instancia de la clase RedisManager que se utiliza para almacenar los tokens en Redis
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * @implNote Este método es el encargado de extraer el nombre de usuario del token
     * @param token Es el token del que se extrae el nombre de usuario
     * @return Retorna el nombre de usuario extraído del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * @implNote Este método es el encargado de extraer la fecha de expiración del token
     * @param token Es el token del que se extrae la fecha de expiración
     * @param claimsResolver Es la función que se utiliza para un claim específico que sea necesario
     * @return Retorna la fecha de expiración extraída del token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @implNote Este método es el encargado de generar el JWT a través de los roles del usuario y el username
     * @param userDetails Es el usuario del que se extraen los roles y el username
     * @return Retorna el JWT generado
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        extraClaims.put("username", userDetails.getUsername());
        return generateToken(extraClaims, userDetails);
    }

    /**
     * @implNote Este método es el encargado de generar el JWT a través de los roles del usuario, el username y los claims extra
     * @param extraClaims Son los claims extra que se desean agregar al JWT
     * @param userDetails Es el usuario del que se extraen los roles y el username
     * @return Retorna el JWT generado
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        String jwt = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        redisManager.storeJwt(jwt);
        return jwt;
    }

    /**
     * @implNote Este método es el encargado de validar si el token es válido
     * @param token Es el token que se desea validar
     * @return Retorna true si el token es válido, false en caso contrario
     */
    public boolean istokenValid(String token){
        return redisManager.isValidToken(token);
    }

    /**
     * @implNote Este método es el encargado de extraer todos los claims del token
     * @param token Es el token del que se extraen los claims
     * @return Retorna todos los claims extraídos del token
     */
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @implNote Este método es el encargado de extraer la llave secreta
     * @return Retorna la llave secreta
     */
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * @implNote Este método es el encargado de extraer los roles del usuario
     * @param claims Son los claims del token
     * @return Retorna los roles extraídos del token
     */
    public Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
        List<String> authorities = claims.get("authorities", List.class);

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
