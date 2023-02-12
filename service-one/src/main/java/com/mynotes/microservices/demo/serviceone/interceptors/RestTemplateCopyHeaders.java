package com.mynotes.microservices.demo.serviceone.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
public class RestTemplateCopyHeaders implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Optional<HttpServletRequest> incomingRequest  = getCurrentHttpRequest();
        if(incomingRequest.isPresent()){
            Enumeration<String> headers = incomingRequest.get().getHeaderNames();
            while (headers.hasMoreElements()){
                String originalRequestHeader = headers.nextElement();
                String originalRequestHeaderValue = incomingRequest.get().getHeader(originalRequestHeader);
                log.info("Original request header={} with value={}",originalRequestHeader,originalRequestHeaderValue);
                if(!request.getHeaders().containsKey(originalRequestHeader)){
                    log.info("Setting request header={} with value={} to new request",originalRequestHeader,originalRequestHeaderValue);
                    request.getHeaders().add(originalRequestHeader, originalRequestHeaderValue);
                }
            }
        }
        return execution.execute(request,body);

    }


    private Optional<HttpServletRequest> getCurrentHttpRequest() {
        return
                Optional.ofNullable(
                                RequestContextHolder.getRequestAttributes()
                        )
                        .filter(ServletRequestAttributes.class::isInstance)
                        .map(ServletRequestAttributes.class::cast)
                        .map(ServletRequestAttributes::getRequest);
    }
}
