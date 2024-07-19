package com.optimus.rest.webservices.restful_web_services.versioning;

import lombok.NonNull;
import lombok.Value;


@Value
public class PersonV1 {
	@NonNull private String name;
}
