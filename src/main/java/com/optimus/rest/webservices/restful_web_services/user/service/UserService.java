package com.optimus.rest.webservices.restful_web_services.user.service;

import com.optimus.rest.webservices.restful_web_services.user.dto.UserDto;
import com.optimus.rest.webservices.restful_web_services.user.mapper.UserMapper;
import com.optimus.rest.webservices.restful_web_services.user.repository.UserRepository;
import com.optimus.rest.webservices.restful_web_services.user.repository.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	@NonNull private UserRepository userRepository;
	@NonNull private UserMapper userMapper;


	public List<UserDto> findAllUsers() {
		return userRepository.findAll()
			.stream()
			.map(userMapper::toDTO)
			.toList();
	}

	public Optional<UserDto> findUserById(int id) {
		return userRepository.findById(id)
			.map(userMapper::toDTO);
	}

	public UserDto createUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		User savedUser = userRepository.save(user);
		return userMapper.toDTO(savedUser);
	}

	public void deleteUserById(int id) {
		userRepository.deleteById(id);
	}
}
