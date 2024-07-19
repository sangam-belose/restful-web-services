package com.optimus.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.List.of;

@RestController
@RequiredArgsConstructor
public class FilteringController {

	@GetMapping("/public")
	public MappingJacksonValue getPublicUserDetails() {
		// return field1, field3
		return applySomeBeanFilter("field1", "field3");
	}

	@GetMapping("/internal")
	public MappingJacksonValue getUserInternalDetails() {
		// return name, dateOfBirth
		return applySomeBeanFilter("field2", "field3");
	}

	private MappingJacksonValue applySomeBeanFilter(String ...fields) {
		List<SomeBean> someBeanList = of(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeanList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
