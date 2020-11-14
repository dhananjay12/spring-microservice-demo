package com.mynotes.microservice.zuulserver;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class TokenFilter extends ZuulFilter {

	@Autowired
	private OauthConfiguration oauthConfiguration;

	@Override
	public int filterOrder() {
		return 6;//PRE_DECORATION_FILTER_ORDER - 1;
	}

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (request.getRequestURI().contains("/token")) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		request.setAttribute("client_secret", oauthConfiguration.getClientSecret());
		 
		System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		return null;
	}
}
