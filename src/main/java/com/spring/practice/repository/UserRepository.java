package com.spring.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.practice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByMobile(String mobile);

	User findByUsername(String username);

}
