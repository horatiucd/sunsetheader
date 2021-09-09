package com.hcd.sunsetheader.event;

import org.springframework.stereotype.Component;

@Component
class SunsetEventListener extends AbstractEventListener<SunsetEvent> {
			
	@Override
	public void onApplicationEvent(SunsetEvent event) {
		event.getValue()
				.ifPresent(date -> event.getResponse().addHeader("Sunset", format(date)));
	}	
}
