package com.spring.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.practice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
