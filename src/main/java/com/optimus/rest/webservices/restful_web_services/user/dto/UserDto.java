package com.optimus.rest.webservices.restful_web_services.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private int id;

	@NotEmpty(message = "Name is required")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Name must contain only letters")
	private String name;

	@NotNull(message = "Date of birth is required")
	@Past(message = "date of birth should be in past")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dateOfBirth;
}
