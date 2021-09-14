package com.hcd.sunsetheader.interceptor;

import com.hcd.sunsetheader.event.DeprecatedResourceEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeprecatedResourceInterceptor extends AnnotatedHandlerMethodInterceptor<DeprecatedResource> {
	
	private final ApplicationEventPublisher eventPublisher;
	
	public DeprecatedResourceInterceptor(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	protected Class<DeprecatedResource> getAnnotationClass() {
		return DeprecatedResource.class;
	}

	@Override
	protected boolean doPreHandle(HttpServletRequest request,
								  HttpServletResponse response, DeprecatedResource annotation) {
		var event = new DeprecatedResourceEvent(this, request, response,
				annotation.since(), annotation.alternate(),
				annotation.policy(), annotation.sunset());
		eventPublisher.publishEvent(event);
		return true;
	}
}
