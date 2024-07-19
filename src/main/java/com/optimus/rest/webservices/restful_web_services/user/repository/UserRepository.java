package com.optimus.rest.webservices.restful_web_services.user.repository;

import com.optimus.rest.webservices.restful_web_services.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
