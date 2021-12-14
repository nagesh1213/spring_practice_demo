package com.spring.practice.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.practice.bean.UserRequestBean;
import com.spring.practice.config.SecurityUtility;
import com.spring.practice.domain.LoginActivity;
import com.spring.practice.domain.Role;
import com.spring.practice.domain.RoleType;
import com.spring.practice.domain.User;
import com.spring.practice.domain.UserRole;
import com.spring.practice.repository.LoginActivityRepository;
import com.spring.practice.repository.RoleRepository;
import com.spring.practice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private LoginActivityRepository loginActivityRepo;

	@Override
	public boolean create(UserRequestBean user) throws Exception {
		log.info("add user service start...{} ", user.getRole());
		ObjectMapper mapper = new ObjectMapper();
		User userData = mapper.convertValue(user, User.class);
		userData.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
		userData.setDecpassword(user.getPassword());
		Role role = new Role();
		if ("USER".equals(user.getRole())) {
			role.setRoleId((long) 1);
			role.setName(RoleType.ROLE_USER.toString());
		} else if ("ADMIN".equals(user.getRole())) {
			role.setRoleId((long) 2);
			role.setName(RoleType.ROLE_ADMIN.toString());
		}
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(userData, role));
		if (!ObjectUtils.isEmpty(createUser(userData, userRoles))) {
			log.info("user successfully saved...!!");
			return true;
		}
		return false;
	}

	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		User localUser = userRepo.findByUsername(user.getUsername());
		if (localUser != null) {
			throw new Exception("user already exists,Nothing will be done.");
		} else {
			for (UserRole ur : userRoles) {
				roleRepo.save(ur.getRole());
			}
		}
		user.getUserRoles().addAll(userRoles);
		localUser = userRepo.save(user);
		return localUser;
	}

	@Override
	public User isExist(@Valid UserRequestBean user) {
		User userIsExist = userRepo.findByEmail(user.getEmail());
		if (ObjectUtils.isEmpty(userIsExist)) {
			userIsExist = userRepo.findByMobile(user.getMobile());
		}
		return userIsExist;
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public void trackLoginActivity(User user) {
		LoginActivity activity = new LoginActivity();
		activity.setUsername(user.getUsername());
		activity.setLogintime(new Date());
		loginActivityRepo.save(activity);
	}

	@Override
	public void trackLogoutActivity(User user) {
		List<LoginActivity> list = loginActivityRepo.findByUsername(user.getUsername());
		if (!ObjectUtils.isEmpty(list) && !list.isEmpty()) {
			LoginActivity activity = list.get(0);
			activity.setLogoutime(new Date());
			loginActivityRepo.save(activity);
		}
	}

	
	@Override
	@Cacheable(value = "users")
	public List<User> fetchAllUsers() {
		return userRepo.findAll();
	}
}
