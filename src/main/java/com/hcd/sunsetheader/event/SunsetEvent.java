package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class SunsetEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	
	private final HttpServletResponse response;
	
	private final Optional<LocalDateTime> value;

	public SunsetEvent(Object source,
			HttpServletResponse response, Optional<LocalDateTime> value) {
		super(source);
		this.response = response;
		this.value = value;
	}
}
