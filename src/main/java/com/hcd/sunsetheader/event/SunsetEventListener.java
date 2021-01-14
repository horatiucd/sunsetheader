package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class SunsetEventListener implements ApplicationListener<SunsetEvent> {
		
	@Override
	public void onApplicationEvent(SunsetEvent event) {
		Optional<LocalDateTime> value = event.getValue();		
		if (value.isPresent()) {
			var sunset = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss z")
					.format(value.get().atZone(ZoneId.of("GMT")));
			
			event.getResponse().addHeader("Sunset", sunset);
		}				
	}	
}
