package com.optimus.rest.webservices.restful_web_services.post.repository;

import com.optimus.rest.webservices.restful_web_services.post.repository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
