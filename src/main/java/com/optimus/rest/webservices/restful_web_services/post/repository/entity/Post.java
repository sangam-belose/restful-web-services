package com.optimus.rest.webservices.restful_web_services.post.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optimus.rest.webservices.restful_web_services.user.repository.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String description;

	@ManyToOne
	@JsonIgnore
	private User user;
}
