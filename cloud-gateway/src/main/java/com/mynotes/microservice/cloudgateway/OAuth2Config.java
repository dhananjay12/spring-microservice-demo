package com.mynotes.microservice.cloudgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
public class OAuth2Config {

    @Bean("oauthAuthenticationWebFilter")
    AuthenticationWebFilter oauthAuthenticationWebFilter(
            OAuth2AuthenticationManagerAdapter authManager, OAuthTokenConverter tokenConverter) {

        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
        filter.setAuthenticationConverter(tokenConverter);
        return filter;
    }

    @Bean
    ResourceServerTokenServices tokenServices(TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(false);
        return tokenServices;
    }

}