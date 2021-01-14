package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
class SunsetEventListener extends AbstractEventListener<SunsetEvent> {
			
	@Override
	public void onApplicationEvent(SunsetEvent event) {
		Optional<LocalDateTime> date = event.getValue();		
		if (date.isPresent()) {	
			event.getResponse().addHeader("Sunset", format(date.get()));
		}				
	}	
}
