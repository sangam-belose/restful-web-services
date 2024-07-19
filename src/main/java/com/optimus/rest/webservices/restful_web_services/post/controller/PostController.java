package com.optimus.rest.webservices.restful_web_services.post.controller;

import com.optimus.rest.webservices.restful_web_services.post.dto.PostCreateRequest;
import com.optimus.rest.webservices.restful_web_services.post.dto.PostDto;
import com.optimus.rest.webservices.restful_web_services.post.repository.entity.Post;
import com.optimus.rest.webservices.restful_web_services.post.service.PostService;
import com.optimus.rest.webservices.restful_web_services.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/posts")
@RequiredArgsConstructor
public class PostController {

	@NonNull private PostService postService;

	@GetMapping
	public List<PostDto> getAllPostsForUser(@PathVariable int userId) throws UserNotFoundException {
		return postService.getAllPostsForUser(userId);
	}

	@PostMapping
	public PostDto createPostForUser(@PathVariable int userId, @Valid @RequestBody PostCreateRequest postCreateRequest) throws UserNotFoundException {
		return postService.createPostForUser(userId, postCreateRequest);
	}
}
