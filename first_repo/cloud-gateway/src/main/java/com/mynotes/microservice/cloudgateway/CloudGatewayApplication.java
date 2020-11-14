package com.mynotes.microservice.cloudgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;



@SpringBootApplication
@EnableEurekaClient
public class CloudGatewayApplication {
	
	
	/*@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("o", p -> p.path("/service/randomfortune")
						.filters(f -> f.setPath("/fortune")
								.hystrix(c -> c.setFallbackUri("forward:/defaultfortune")))
						.uri("lb://fortune"))
				.route("hello_rewrite", p -> p.path("/service/hello/**")
						.filters(f -> f.filter((exchange, chain) -> {
							String name = exchange.getRequest().getQueryParams().getFirst("name");
							String path = "/hello/"+name;
							ServerHttpRequest request = exchange.getRequest().mutate()
									.path(path)
									.build();
							return chain.filter(exchange.mutate().request(request).build());
						}))
						.uri("lb://hello"))
				.route("index", p -> p.path("/")
						.filters(f -> f.setPath("/index.html"))
						.uri("lb://ui"))
				.route("ui", p -> p.path("/").or().path("/css/**").or().path("/js/**")
						.uri("lb://ui"))
				.route("monolith", p -> p.path("/**")
						.uri("http://localhost:8081"))
				.build();
	}*/
	
	@Value("${app.security.jwt.public-key}")
	private String PUBLIC_KEY;
	
	@Value("${app.security.jwt.client-id}")
	private String CLIENT_ID;

	@Autowired
	private JwtConverter jwtConverter;

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
	
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setAccessTokenConverter(jwtConverter);
		//converter.setSigningKey(PUBLIC_KEY); //THis is private key
		converter.setVerifierKey(PUBLIC_KEY);
		return converter;
	}
	
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
