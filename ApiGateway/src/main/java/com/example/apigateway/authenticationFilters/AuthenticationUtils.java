package com.example.apigateway.authenticationFilters;

import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthenticationUtils {

    private final String ADMIN_ROLE = "ADMIN";
    private final String OPERATOR_ROLE = "OPERADOR";
    private final String CONSULTING_ROLE = "CONSULTOR";
    private final String CLIENT_ROLE = "CLIENTE";

    public Mono<Void> sendUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public ResponseEntity<String> getUserRole(String token) {
        RestTemplate isAuthenticatedRequest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //return isAuthenticatedRequest.exchange("http://localhost:8086/v1/is-authenticated", HttpMethod.GET, entity, String.class);
        return isAuthenticatedRequest.exchange("lb://authentication-service/v1/is-authenticated", HttpMethod.GET, entity, String.class);
    }

    public Mono<Void> sendForbbiden(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public boolean hasAdminRole(ResponseEntity<String> decodedRole) {
        return decodedRole.getBody().equals(ADMIN_ROLE);
    }

    public boolean hasOperatorRoleOrAbove(ResponseEntity<String> decodedRole) {
        return
            decodedRole.getBody().equals(OPERATOR_ROLE)
            || decodedRole.getBody().equals(ADMIN_ROLE);
    }

    public boolean hasConsultingRoleOrAbove(ResponseEntity<String> decodedRole) {
        return decodedRole.equals(CONSULTING_ROLE)
                || hasOperatorRoleOrAbove(decodedRole);
    }

    public boolean hasClientRoleOrAbove(ResponseEntity<String> decodedRole){
        return decodedRole.equals(CLIENT_ROLE)
                || hasConsultingRoleOrAbove(decodedRole);
    }
}
