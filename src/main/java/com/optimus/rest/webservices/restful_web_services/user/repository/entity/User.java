package com.optimus.rest.webservices.restful_web_services.user.repository.entity;

import com.optimus.rest.webservices.restful_web_services.post.repository.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details")
@Data
public class User {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
}
