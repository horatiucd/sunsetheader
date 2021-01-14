package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
class DeprecatedApiEventListener implements ApplicationListener<DeprecatedApiEvent> {

	private final ApplicationEventPublisher eventPublisher;
		
	public DeprecatedApiEventListener(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public void onApplicationEvent(DeprecatedApiEvent event) {			
		var deprecation = event.getSince().isBlank() ? String.valueOf(true) : event.getSince();
		event.getResponse().addHeader("Deprecation", deprecation);
		
		var link = formatLink(contextPath(event.getRequest()) + event.getAlternate(), "alternate") + "," +
				   formatLink(event.getPolicy(), "deprecation");
		event.getResponse().addHeader(HttpHeaders.LINK, link);
		
		if (!event.getSunset().isBlank()) {
			var sunset = LocalDateTime.parse(event.getSunset(), 
					DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss z"));
			
			eventPublisher.publishEvent(new SunsetEvent(this, event.getResponse(), Optional.of(sunset))); 
					
		}		
	}
	
	static String formatLink(String uri, String rel) {
		return String.format("<%s>; rel=\"%s\"", uri, rel);
	}
	
	static String contextPath(HttpServletRequest request) {	
		return String.format("%s://%s%d%s",
				request.getScheme(), request.getServerName(), 
				request.getServerPort(), request.getContextPath());
	}
}
