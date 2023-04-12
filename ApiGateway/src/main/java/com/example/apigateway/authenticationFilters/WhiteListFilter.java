package com.example.apigateway.authenticationFilters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class WhiteListFilter extends AbstractGatewayFilterFactory<WhiteListFilter.Config> {

    @Override
    public WhiteListFilter.Config newConfig() { return new WhiteListFilter.Config(); }

    @Override
    public GatewayFilter apply(WhiteListFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            AuthenticationUtils authUtils = new AuthenticationUtils();
            final List<String> apiEndpoints = List.of(
                    "/authentication-service/v1/auth/register",
                    "/authentication-service/v1/auth/authenticate"
            );
            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if(isApiSecured.test(request)){
                if(!request.getHeaders().containsKey("Authorization")){
                    return authUtils.sendUnauthorized(exchange);
                }

                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

                try{

                    ResponseEntity<String> isValidToken = authUtils.getUserRole(token);
                    if(isValidToken.getStatusCode() != HttpStatus.OK) return authUtils.sendUnauthorized(exchange);
                    if(!authUtils.hasOperatorRoleOrAbove(isValidToken)) return authUtils.sendForbbiden(exchange);

                }catch(Exception e){
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    return response.setComplete();
                }
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public String name() {
        return "WhiteListFilter";
    }

    public static class Config {
        //Nada, configuración limpia
    }

}
