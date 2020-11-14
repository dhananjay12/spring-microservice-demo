package com.mynotes.microservice.cloudgateway;

import java.util.function.Function;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Component
public class OAuthTokenConverter implements Function<ServerWebExchange, Mono<Authentication>> {
	
	
	 private static final String BEARER = "bearer ";

	    @Override
	    public Mono<Authentication> apply(ServerWebExchange exchange) {
	        String token = extractToken(exchange.getRequest());
	        if (token != null) {
	            return Mono.just(new PreAuthenticatedAuthenticationToken(token, ""));
	        }
	        return Mono.empty();
	    }

	    private String extractToken(ServerHttpRequest request) {
	        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
	        if (StringUtils.isBlank(token) || !token.toLowerCase().startsWith(BEARER)) {
	            return null;
	        }
	        return token.substring(BEARER.length());
	    }

}
