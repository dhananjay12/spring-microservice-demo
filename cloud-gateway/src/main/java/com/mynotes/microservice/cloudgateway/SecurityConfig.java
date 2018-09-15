package com.mynotes.microservice.cloudgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    /**
     * The configuration defined here is what really drives the edge service.
     * Any request that doesn't get handled by the other two configs will be
     * handled by this one. All requests coming through here must have a
     * valid access token
     */
    @Bean
    SecurityWebFilterChain oauthTokenAuthConfig(
            ServerHttpSecurity security, AuthenticationWebFilter oauthAuthenticationWebFilter) {

        return security
                .csrf().disable()
                .logout().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling().and()
                .securityMatcher(notMatches("/token/**"))
                .addFilterAt(oauthAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange().anyExchange().authenticated()
                .and().build();
    }

    private ServerWebExchangeMatcher matches(String ... routes) {
        return ServerWebExchangeMatchers.pathMatchers(routes);
    }

    private ServerWebExchangeMatcher notMatches(String ... routes) {
        return new NegatedServerWebExchangeMatcher(matches(routes));
    }
    
}
