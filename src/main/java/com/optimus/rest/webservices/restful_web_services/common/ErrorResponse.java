package com.optimus.rest.webservices.restful_web_services.common;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorResponse {
	private int errorCode;
	private String message;
	private List<String> errors;
	@Builder.Default
	private long timestamp = System.currentTimeMillis();
}
