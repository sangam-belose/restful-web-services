package com.optimus.rest.webservices.restful_web_services.post.service;

import com.optimus.rest.webservices.restful_web_services.post.dto.PostCreateRequest;
import com.optimus.rest.webservices.restful_web_services.post.dto.PostDto;
import com.optimus.rest.webservices.restful_web_services.post.mapper.PostMapper;
import com.optimus.rest.webservices.restful_web_services.post.repository.PostRepository;
import com.optimus.rest.webservices.restful_web_services.post.repository.entity.Post;
import com.optimus.rest.webservices.restful_web_services.user.exception.UserNotFoundException;
import com.optimus.rest.webservices.restful_web_services.user.repository.UserRepository;
import com.optimus.rest.webservices.restful_web_services.user.repository.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

	@NonNull private PostRepository postRepository;
	@NonNull private UserRepository userRepository;
	@NonNull PostMapper postMapper;

	public List<PostDto> getAllPostsForUser(int userId) throws UserNotFoundException {
		return userRepository.findById(userId)
			.map(User::getPosts)
			.orElseThrow(() -> new UserNotFoundException("User not found"))
			.stream()
			.map(post -> postMapper.toDto(post))
			.toList();
	}

	public PostDto createPostForUser(int userId, PostCreateRequest postCreateRequest) throws UserNotFoundException {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found"));

		Post post = new Post();
		post.setDescription(postCreateRequest.getDescription());
		post.setUser(user);
		postRepository.save(post);
		return postMapper.toDto(post);
	}

}
