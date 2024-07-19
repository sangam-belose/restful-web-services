package com.optimus.rest.webservices.restful_web_services.post.mapper;

import com.optimus.rest.webservices.restful_web_services.post.dto.PostDto;
import com.optimus.rest.webservices.restful_web_services.post.repository.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

	public PostDto toDto(Post post) {
		if(post == null)
			return null;

		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setDescription(post.getDescription());
		postDto.setUserName(post.getUser().getName());
		return postDto;
	}

	public Post toEntity(PostDto postDto) {
		if(postDto == null)
			return null;

		Post post = new Post();
		post.setId(postDto.getId());
		post.setDescription(postDto.getDescription());
		return post;
	}
}
