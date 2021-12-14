package com.spring.practice.service;

import java.util.List;

import javax.validation.Valid;

import com.spring.practice.bean.UserRequestBean;
import com.spring.practice.domain.User;

public interface UserService {

	boolean create(UserRequestBean user) throws Exception;

	User isExist(@Valid UserRequestBean user);

	User findByUsername(String username);

	void trackLoginActivity(User user);

	void trackLogoutActivity(User user);

	List<User> fetchAllUsers();

}
