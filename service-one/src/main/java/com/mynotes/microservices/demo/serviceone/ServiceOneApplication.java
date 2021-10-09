package com.mynotes.microservices.demo.serviceone;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
public class ServiceOneApplication {

	static Logger LOGGER = LoggerFactory.getLogger(ServiceOneApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceOneApplication.class, args);

	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = null;
		LOGGER.info("Is Debug Enabled:"+ LOGGER.isDebugEnabled());
		if (LOGGER.isDebugEnabled()) {
			ClientHttpRequestFactory factory
					= new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
			List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
			if (CollectionUtils.isEmpty(interceptors)) {
				interceptors = new ArrayList<>();
			}
			interceptors.add(new LoggingInterceptor());
			restTemplate.setInterceptors(interceptors);
			restTemplate = new RestTemplate(factory);

		} else {
			restTemplate = new RestTemplate();
		}

		return restTemplate;
	}

}
