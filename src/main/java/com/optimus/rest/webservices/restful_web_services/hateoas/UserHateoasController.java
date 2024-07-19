package com.optimus.rest.webservices.restful_web_services.hateoas;

import com.optimus.rest.webservices.restful_web_services.user.dto.UserDto;
import com.optimus.rest.webservices.restful_web_services.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class UserHateoasController {

	@NonNull private UserService userDaoService;

	@GetMapping("/usershateoas")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ok(userDaoService.findAllUsers());
	}

	@GetMapping("/usershateoas/{id}")
	public EntityModel<UserDto> getUserById(@PathVariable Integer id) {
		UserDto user = userDaoService.findUserById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("id %s not found", id))
		);

		EntityModel userModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		userModel.add(link.withRel("all-users"));
		return userModel;
	}
}
