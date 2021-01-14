package com.hcd.sunsetheader.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class DeprecatedApiEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
		
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	private final String since;
	private final String alternate;
	private final String policy;	
	private final String sunset;
			
	public DeprecatedApiEvent(Object source,  
			HttpServletRequest request, HttpServletResponse response,
			String since, String alternate, String policy,
			String sunset) {
		super(source);
		this.request = request;
		this.response = response;
		this.since = since;
		this.alternate = alternate;
		this.policy = policy;
		this.sunset = sunset;
	}
		
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public String getSince() {
		return since;
	}
	
	public String getAlternate() {
		return alternate;
	}
	
	public String getPolicy() {
		return policy;
	}
	
	public String getSunset() {
		return sunset;
	}
}
