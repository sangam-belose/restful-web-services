package com.optimus.rest.webservices.restful_web_services.versioning;

import com.optimus.rest.webservices.restful_web_services.versioning.PersonV2.Name;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

	@GetMapping("/v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("Bob");
	}

	@GetMapping("/v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("Bob", "charlie"));
	}

	@GetMapping(value = "/person", params = "version=1")
	public PersonV1 getPersonVByRequestParamVersioningv1() {
		return new PersonV1("Bob");
	}

	@GetMapping(value = "/person", params = "version=2")
	public PersonV2 getPersonVByRequestParamVersioningv2() {
		return new PersonV2(new Name("Bob", "charlie"));
	}

	@GetMapping(value = "/person", headers = "X-API-VERSION=1")
	public PersonV1 getPersonVByHeadersv1() {
		return new PersonV1("Bob");
	}

	@GetMapping(value = "/person", headers = "X-API-VERSION=2")
	public PersonV2 getPersonVByHeadersv2() {
		return new PersonV2(new Name("Bob", "charlie"));
	}

	@GetMapping(value = "/person", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getPersonVByMediaTypev1() {
		return new PersonV1("Bob");
	}

	@GetMapping(value = "/person", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getPersonVByMediaTypev2() {
		return new PersonV2(new Name("Bob", "charlie"));
	}
}
