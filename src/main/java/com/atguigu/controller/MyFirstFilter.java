package com.atguigu.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFirstFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("MyFilter1.doFilter(我是第一个过滤器)");
		chain.doFilter(request, response);
		System.out.println("MyFilter1.doFilter(我是第一个过滤器2)");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
