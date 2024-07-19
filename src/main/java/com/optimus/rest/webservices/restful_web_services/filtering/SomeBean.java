package com.optimus.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Value;

@Value
@JsonFilter("someBeanFilter")
public class SomeBean {
	private String field1;
	private String field2;
	private String field3;

}
