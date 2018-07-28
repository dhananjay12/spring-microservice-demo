package com.mynotes.microservice.common.jwtresoureserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.util.StringUtils;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${security.exclude.url:}")
	private String EXCLUDE_MATCHERS;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(!StringUtils.isEmpty(EXCLUDE_MATCHERS)) {
			http.authorizeRequests()
			.antMatchers(EXCLUDE_MATCHERS).permitAll();
		}
		
		http.authorizeRequests()
		.anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER);
		
		//.antMatchers("/", "/login", "/mobile/login", "/api/auth/**", "/reservations/**").permitAll()
	}
}
