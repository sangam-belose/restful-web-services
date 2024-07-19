package com.optimus.rest.webservices.restful_web_services.user.controller;

import com.optimus.rest.webservices.restful_web_services.user.service.UserService;
import com.optimus.rest.webservices.restful_web_services.user.dto.UserDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

	@Nonnull private UserService userDaoService;
	@Nonnull private MessageSource messageSource;

	@GetMapping("/greeting")
	public String sayHello() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "defaultMessage", locale);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ok(userDaoService.findAllUsers());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
		UserDto user = userDaoService.findUserById(id)
			.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("id %s not found", id)));
		return ok(user);
	}

	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
		UserDto createdUser = userDaoService.createUser(user);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(createdUser.getId())
			.toUri();

		return created(location).body(createdUser);
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userDaoService.deleteUserById(id);
	}

}
