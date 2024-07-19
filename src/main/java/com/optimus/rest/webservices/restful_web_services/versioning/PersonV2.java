package com.optimus.rest.webservices.restful_web_services.versioning;

import lombok.NonNull;
import lombok.Value;

@Value
public class PersonV2 {
	@NonNull private Name name;

	@Value
	public static class Name {
		@NonNull private String firstName;
		@NonNull private String lastName;

	}
}
