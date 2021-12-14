package com.spring.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.practice.domain.LoginActivity;

public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {

	@Query("select activity from LoginActivity activity where activity.username = ?1 order by activity.logintime desc")
	List<LoginActivity> findByUsername(String username);

}
