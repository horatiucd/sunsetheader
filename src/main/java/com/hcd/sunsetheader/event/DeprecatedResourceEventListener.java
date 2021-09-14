package com.hcd.sunsetheader.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
		final LocalDateTime since = parse(event.getSince());
		return since != null ? event.getSince() : String.valueOf(true);
	}
	
	private String link(DeprecatedResourceEvent event) {
		final String alternateLink = formatLink(contextPath(event.getRequest()) + event.getAlternate(), "alternate");
		final String deprecationLink = formatLink(event.getPolicy(), "deprecation");
		return alternateLink + "," + deprecationLink;
	}
}
