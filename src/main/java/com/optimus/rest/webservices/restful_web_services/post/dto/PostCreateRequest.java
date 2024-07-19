package com.optimus.rest.webservices.restful_web_services.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostCreateRequest {
	@NotBlank
	@Size(min = 2, max = 30)
	private String description;
}
