package com.optimus.rest.webservices.restful_web_services.user.mapper;

import com.optimus.rest.webservices.restful_web_services.user.dto.UserDto;
import com.optimus.rest.webservices.restful_web_services.user.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public User toEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}

		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setDateOfBirth(userDto.getDateOfBirth());

		return user;
	}

	public UserDto toDTO(User user) {
		if (user == null) {
			return null;
		}

		UserDto userDTO = new UserDto();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setDateOfBirth(user.getDateOfBirth());

		return userDTO;
	}
}
