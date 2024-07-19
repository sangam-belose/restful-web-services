package com.optimus.rest.webservices.restful_web_services.post.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDto {
		private UUID id;
		private String description;
		private String userName;
}
