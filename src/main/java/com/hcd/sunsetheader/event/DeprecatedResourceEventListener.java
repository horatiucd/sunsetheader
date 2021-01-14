package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
class DeprecatedResourceEventListener extends AbstractEventListener<DeprecatedResourceEvent> {

	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public DeprecatedResourceEventListener(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public void onApplicationEvent(DeprecatedResourceEvent event) {		
		event.getResponse().addHeader("Deprecation", deprecation(event));
		event.getResponse().addHeader(HttpHeaders.LINK, link(event));
				
		eventPublisher.publishEvent(new SunsetEvent(this, 
				event.getResponse(), parse(event.getSunset())));
	}
	
	private String deprecation(DeprecatedResourceEvent event) {
		Optional<LocalDateTime> since = parse(event.getSince());
		if (since.isPresent()) {
			return event.getSince();
		}
		return String.valueOf(true);
	}
	
	private String link(DeprecatedResourceEvent event) {
		return formatLink(contextPath(event.getRequest()) + event.getAlternate(), "alternate") + "," + 
				formatLink(event.getPolicy(), "deprecation");
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
