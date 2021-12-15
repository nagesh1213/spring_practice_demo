package com.spring.practice.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.practice.bean.UserRequestBean;
import com.spring.practice.domain.User;
import com.spring.practice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PublicController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home(HttpServletRequest request) {
		log.info("Public Controller....");
		return "common/login";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		return "common/login";
	}

	@GetMapping("/logout_user")
	public String logout(HttpServletRequest request, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if (user != null) {
//			userService.trackLogoutActivity(user);
		}
		return "redirect:/logout";
	}

	@GetMapping("/create")
	public String create(Model model, HttpServletRequest request, Principal principal) {
		model.addAttribute("user", new UserRequestBean());
		return "common/registration";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("user") UserRequestBean user, BindingResult result, Model model,
			RedirectAttributes redirect) {
		log.info("create user controller start...");
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "common/registration";
		}
		if (!ObjectUtils.isEmpty(user) && ObjectUtils.isEmpty(userService.isExist(user))) {
			try {
				if (userService.create(user)) {
					redirect.addFlashAttribute("isRegister", true);
					return "redirect:/login";
				}
			} catch (Exception e) {
				log.error("Error in create user controller...");
				model.addAttribute("isExist", false);
			}
			model.addAttribute("isExist", false);
		}
		model.addAttribute("isExist", true);
		return "common/registration";
	}

	@RequestMapping("/home")
	public String userDashboard(HttpServletRequest request, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		if (user != null) {
//			userService.trackLoginActivity(user);
			if ("ADMIN".equals(user.getRole())) {
				List<User> users = userService.fetchAllUsers();
				model.addAttribute("users", users);
			}
			return "common/home";
		}
		return "redirect:/logout";
	}

}
