package com.hcd.sunsetheader.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

abstract class AbstractEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {

	protected final DateTimeFormatter formatter;
	
	protected AbstractEventListener() {
		formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss z");
	}
	
	protected Optional<LocalDateTime> parse(String date) {
		LocalDateTime value;
		try {
			value = LocalDateTime.parse(date, formatter);
		} catch (DateTimeParseException e) {
			value = null;
		}
		return Optional.ofNullable(value);
	}
	
	protected String format(LocalDateTime date) {
		return formatter.format(date.atZone(ZoneId.of("GMT")));
	}
}
