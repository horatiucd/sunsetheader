package com.hcd.sunsetheader.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Getter
public class SunsetEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	
	private final HttpServletResponse response;
	
	private final LocalDateTime value;

	public SunsetEvent(Object source,
			HttpServletResponse response, LocalDateTime value) {
		super(source);
		this.response = response;
		this.value = value;
	}
}
