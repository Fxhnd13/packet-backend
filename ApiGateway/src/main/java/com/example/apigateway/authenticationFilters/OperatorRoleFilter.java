package com.example.apigateway.authenticationFilters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class OperatorRoleFilter extends AbstractGatewayFilterFactory<OperatorRoleFilter.Config> {

    public OperatorRoleFilter(){ super(OperatorRoleFilter.Config.class); }

    @Override
    public OperatorRoleFilter.Config newConfig() { return new OperatorRoleFilter.Config(); }

    @Override
    public GatewayFilter apply(OperatorRoleFilter.Config config) {
        return (exchange, chain) -> {
            AuthenticationUtils authUtils = new AuthenticationUtils();
            ServerHttpRequest request = exchange.getRequest();

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
            return chain.filter(exchange);
        };
    }

    @Override
    public String name() {
        return "OperatorRoleFilter";
    }

    public static class Config {
        //Nada, configuración limpia
    }
}
