package com.spring.practice.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.practice.domain.User;
import com.spring.practice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private UserService service;

	@GetMapping("/fetchAllUsers")
	public String fetchAllUsers(Model model,Principal principal) {
		log.info("fetching all users....");
		User user = service.findByUsername(principal.getName());
		if(!ObjectUtils.isEmpty(user)) {
			List<User> users = service.fetchAllUsers();
			model.addAttribute("users",users);
			log.info("Users : {} ",users);
			return "admin/admin_home";
		}
		return "redirect:/logout_user";
		
	}

}
