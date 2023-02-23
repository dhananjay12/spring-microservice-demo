package com.mynotes.microservices.demo.serviceone;

import com.mynotes.microservices.demo.serviceone.interceptors.LoggingRequestResponse;
import com.mynotes.microservices.demo.serviceone.interceptors.RestTemplateCopyHeaders;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.kubernetes.fabric8.loadbalancer.Fabric8ServiceInstanceMapper;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class ServiceOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOneApplication.class, args);

    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = null;
        log.info("Is Debug Enabled:" + log.isDebugEnabled());
        if (log.isDebugEnabled()) {
            ClientHttpRequestFactory factory
                    = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
            restTemplate = new RestTemplate(factory);
            List<ClientHttpRequestInterceptor> interceptors = getInterceptors(restTemplate);
            interceptors.add(new RestTemplateCopyHeaders());
            interceptors.add(new LoggingRequestResponse());
            restTemplate.setInterceptors(interceptors);
        } else {
            restTemplate = new RestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = getInterceptors(restTemplate);
            interceptors.add(new RestTemplateCopyHeaders());
            restTemplate.setInterceptors(interceptors);
        }

        return restTemplate;
    }

    private List<ClientHttpRequestInterceptor> getInterceptors(RestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        return interceptors;
    }

    @Bean
	@Primary
    public ServiceInstanceListSupplier fabric8ServicesListSupplier(
            Environment environment, KubernetesClient kubernetesClient, Fabric8ServiceInstanceMapper mapper,
            KubernetesDiscoveryProperties discoveryProperties,
            ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder().withBase(new Fabric8ServicesListSupplier(
                        environment, kubernetesClient, mapper, discoveryProperties))
                .withCaching().build(context);
    }
}
