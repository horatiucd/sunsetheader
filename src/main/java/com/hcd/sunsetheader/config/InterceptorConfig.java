package com.hcd.sunsetheader.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hcd.sunsetheader.interceptor.DeprecatedResourceInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	private DeprecatedResourceInterceptor interceptor;

	@Autowired
	public void setInterceptor(DeprecatedResourceInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}
}
