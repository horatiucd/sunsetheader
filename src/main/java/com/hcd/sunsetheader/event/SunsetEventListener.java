package com.hcd.sunsetheader.event;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class SunsetEventListener extends AbstractEventListener<SunsetEvent> {
			
	@Override
	public void onApplicationEvent(SunsetEvent event) {
		final LocalDateTime date = event.getValue();
		if (date != null) {
			event.getResponse().addHeader("Sunset", format(date));
		}
	}	
}
