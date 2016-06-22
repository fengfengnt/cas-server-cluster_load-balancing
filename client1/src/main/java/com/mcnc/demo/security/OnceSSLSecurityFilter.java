package com.mcnc.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class OnceSSLSecurityFilter extends GenericFilterBean{
	private volatile boolean isAlreadyFilter = false;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(!isAlreadyFilter) {
			isAlreadyFilter = true;
			DefaultSSLConfigContextHolder.removeDefaultRestriction();
		}
		
		chain.doFilter(request, response);
	}

}
