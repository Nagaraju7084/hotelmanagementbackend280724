package com.hotel.backend.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
	
	//by default spring boot, spring security will not allow any api calls
	//from different origins like angular application
	//in this file, we will write the code to allow the api calls
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		String originHeader = httpServletRequest.getHeader("origin");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
		
		if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) { //some browsers hit options api call before calling the actual api
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(httpServletRequest, httpServletResponse);
		}
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}
	
}
