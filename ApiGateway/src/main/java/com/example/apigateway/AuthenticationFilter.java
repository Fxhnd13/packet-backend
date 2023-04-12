package com.example.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ObjectInputFilter;
import java.util.List;
import java.util.function.Predicate;

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter(String authorizedRole) { super(Config.class); }

    @Override
    public Config newConfig() {
        return new Config();
    }

    @Override
    public String name() {
        return "AuthenticationFilter";
    }

    public static class Config {
        // configuración del filtro aquí
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            final List<String> apiEndpoints = List.of(
                    "/authentication-service/v1/auth/register",
                    "/authentication-service/v1/auth/authenticate"
            );
            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if(isApiSecured.test(request)){
                if(!request.getHeaders().containsKey("Authorization")){
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

                try{
                    RestTemplate isAuthenticatedRequest = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", token);
                    HttpEntity<String> entity = new HttpEntity<>(headers);

                    //ResponseEntity<Boolean> isAuthenticatedResponse = isAuthenticatedRequest.exchange("http://localhost:8086/v1/is-authenticated", HttpMethod.GET, entity, Boolean.class);
                    ResponseEntity<Boolean> isAuthenticatedResponse = isAuthenticatedRequest.exchange("lb://authentication-service/v1/is-authenticated", HttpMethod.GET, entity, Boolean.class);
                    if(isAuthenticatedResponse.getStatusCode() != HttpStatus.OK){
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(isAuthenticatedResponse.getStatusCode());
                        return response.setComplete();
                    }

                }catch (Exception e){
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    return response.setComplete();
                }
            }

            return chain.filter(exchange);
        };
    }
}
