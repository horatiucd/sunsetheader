package com.hcd.sunsetheader.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hcd.sunsetheader.event.DeprecatedResourceEvent;

@Component
public class DeprecatedResourceInterceptor implements HandlerInterceptor {
	
	private final ApplicationEventPublisher eventPublisher;
	
	public DeprecatedResourceInterceptor(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			DeprecatedResource deprecated = handlerMethod.getMethod().getAnnotation(DeprecatedResource.class);
	        if (deprecated == null) {        	
	            return true;
	        }
			
	        var event = new DeprecatedResourceEvent(this, request, response, 
	        		deprecated.since(), deprecated.alternate(), 
	        		deprecated.policy(), deprecated.sunset());
	        eventPublisher.publishEvent(event);
		}
        
		return true;
	}
}
